//
//  VenueView.swift
//  RobotConf
//
//  Created by Antoine Robiez on 15/03/2024.
//  Copyright © 2024 Djavan Bertrand. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct VenueView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return VenueViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
