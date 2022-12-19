## Kotlin Multiplatform multi-module test app

This document describes (or tries to describe) a couple of architectural decisions, shortcomings and next steps

## State of things

### Android
All the basic functional requirements are met:
* [x] Provide an interface for inputting search terms
* [x] Display 25 results for the given search term, including a thumbnail of the image and the title
* [x] Selecting a thumbnail or title displays the full photo
* [x] Provide a way to return to the search results
* [x] Provide a way to search for another term

Additionally,
* [x] Save prior search terms

![telegram-cloud-document-2-5260718029972448218.gif](images%2Ftelegram-cloud-document-2-5260718029972448218.gif)

### iOS
Only proof-of-concept on iOS is built, which fetches things from API and stores results in database (all using Kotlin!)


| Logs                                   | Screenshot (these are ids)                         |
|----------------------------------------|----------------------------------------------------|
| ![ios-logs.png](images%2Fios-logs.png) | ![ios-screenshot.png](images%2Fios-screenshot.png) |

## Learnings
* I'd say it's still too early for full-scale production applications
  * Some experiments can already be started though
* iOS integration is harder:
  * Android is configured via Gradle and there are little to no problems with module depepndencies
  * iOS is linked dynamically [(static linking is not supported)](https://github.com/cashapp/sqldelight/issues/1442#issuecomment-1295857391)
  * [SQLite3 needs to be linked manually](https://github.com/cashapp/sqldelight/issues/1442)
    * For that, i migrated the setup to [use CocoaPods](https://kotlinlang.org/docs/native-cocoapods.html#set-up-an-environment-to-work-with-cocoapods)
    * Still, the problem wasn't documented well enough, so the solution on the latest Multiplatform was to add the following: 
    * `cocoapods.framework.linkerOpts += "-lsqlite3"`
  * Majority of applications use only one shared module - i set up 4 of them
    * Configuration sharing between them is not possible, because my IntelliJ crashes when i am trying to [add buildSrc folder to share configurations](https://docs.gradle.org/current/userguide/custom_plugins.html#sec:packaging_a_plugin) 

All in all, i spent enormous time setting things up and defining the model layer, but it was mostly because i was doing it the first time ever. And it's quite surprising how many things just worked keeping in mind this is still in beta.

## Architecture

This project uses MVVM (encouraged by Google).

Model is fully cross-platform (written in Kotlin), using Kotlin Flows, Ktor as a REST client and SQLite library.
ViewModel is native both for iOS and Android.
View is Jetpack Compose for Android, SwiftUI for iOS. 

### Model

![architecture-model.png](images%2Farchitecture-model.png)

Every module has its own responsibility and encapsulates a particular functionality:
* `:model` hides the serialization and runs compiler plugin responsible for converting things into JSON
* `:database` encapsulates all the database operations, exposing two native interfaces for iOS and Android to provide dependencies
* `:network` is a shared, pre-configured HTTP client exposing only one method to be consumed outside
  * Also, API key is stored here
  * **It's discouraged to store API keys in GitHub, but it's still the easiest way to do it, and it's a test task, so i won't use another mechanism for the sake of simplicity**
* Finally, `:shared` is the container for our Repository, which takes different data sources and combines them together. 

### Dependency Injection

No framework is used, see [Dependencies.kt](shared%2Fsrc%2FcommonMain%2Fkotlin%2Fcom%2Fexample%2Fwbdtestapp%2Fdi%2FDependencies.kt) and [DatabaseDependencies.kt](database%2Fsrc%2FcommonMain%2Fkotlin%2Fcom%2Fexample%2Fwbdtestapp%2Fdatabase%2FDatabaseDependencies.kt) for implementation details

### ViewModel

ViewModel with this approach is responsible for converting user intentions into code and vise versa. 

![architecture-vm.png](images%2Farchitecture-vm.png)

The drawback is also obvious on the schema: ViewModel knows about data models, because it's performing the conversion to the UI object. 
Ideally, i'd resolve this by introducing a UIModel module which would live in-between the two layers and be responsible for conversions itself. 
Then ViewModel would only depend on UIModel. 

*Why we need UIModel at all?*

This helps with making UI functions pure, free of side effects. Without UIModel classes, we map different parts of the business logic onto the UI. Instead, we can leverage ViewModel to Combine different sources into one object (or sealed class, like [we do in this case](androidApp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fwbdtestapp%2Fandroid%2Fviewmodel%2FSearchResultsViewModel.kt)). Then UI job is simple take that class and render itself.

(Combine with capital C because [there is an Apple framework](https://developer.apple.com/documentation/combine) which does exactly that)
### View 

UI layer is trivial, see [composables](androidApp%2Fsrc%2Fmain%2Fjava%2Fcom%2Fexample%2Fwbdtestapp%2Fandroid%2Fcomposables) folder for details. 