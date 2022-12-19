//
//  ViewModel.swift
//  iosApp
//
//  Created by Nikita Kraev on 12/18/22.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import shared
import KMPNativeCoroutinesCombine
import Combine

class ViewModel: ObservableObject {
    @Published var searchTerm: String = "sunset"
    
    @Published private(set) var result: [ModelPhoto] = []
    @Published private(set) var isSearching = false
    
    private let deps = Dependencies()
    private var cancelables = Set<AnyCancellable>()
    
    init() {
        let cancelable = $searchTerm
            .removeDuplicates()
            .flatMap { [self] in createPublisher(for: deps.photosRepo.getPhotosNative(query: $0)) }
            .receive(on: DispatchQueue.main)
            .sink(receiveCompletion: {
                print(">> [iOS] Error: \($0)")
            }, receiveValue: { [weak self] in
                print(">> [iOS] Success, photos: \($0)")
                self?.result = $0
            })
        
        cancelables.insert(cancelable)
    }
    
    deinit {
        cancelables.forEach { $0.cancel() }
    }
}
