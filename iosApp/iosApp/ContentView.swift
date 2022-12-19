import SwiftUI

struct ContentView: View {
	let greet = "Test"
    @StateObject private var viewModel = ViewModel()

    var body: some View {
        Button("Search for mountain", action: {
            viewModel.searchTerm = "mountain"
        })
        
        List(viewModel.result, id: \.id) {
            Text("\($0.id)")
        }
	}
}
