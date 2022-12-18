//
//  ViewModel.swift
//  iosApp
//
//  Created by Nikita Kraev on 12/18/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared

class ViewModel: ObservableObject {
    @Published var searchTerm: String = "sunset"
    
    @Published private(set) var result: [ModelPhoto] = []
    @Published private(set) var isSearching = false
    
    private let deps = Dependencies()
    private var searchTask: Task<Void, Never>?
    
    @MainActor
    func executeQuery() async {
        searchTask?.cancel()
        let currentSearchTerm = searchTerm.trimmingCharacters(in: .whitespaces)
        if currentSearchTerm.isEmpty {
            result = []
            isSearching = false
        }
        else {
            searchTask = Task {
                isSearching = true
                result = (try? await deps.photosRepo.getPhotos(query: searchTerm)) ?? []
                print(">> [iOS] Received list: \(result)")
                if !Task.isCancelled {
                    isSearching = false
                }
            }
        }
    }
}
