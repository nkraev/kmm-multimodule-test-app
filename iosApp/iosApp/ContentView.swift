import SwiftUI

struct ContentView: View {
	let greet = "Test"
    @StateObject private var viewModel = ViewModel()

    var body: some View {
        Button("Request data from API", action: {
            Task { await viewModel.executeQuery() }
        })
        List(viewModel.result, id: \.id) {
            Text($0.id)
        }
	}
}
