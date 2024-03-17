//
//  SponsorsView.swift
//  RobotConf
//
//  Created by Antoine Robiez on 14/03/2024.
//  Copyright © 2024 Djavan Bertrand. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SponsorsView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        return SponsorViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
