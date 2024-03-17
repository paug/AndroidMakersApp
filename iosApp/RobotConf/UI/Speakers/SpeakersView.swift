//
//  SpeakersView.swift
//  RobotConf
//
//  Created by Antoine Robiez on 15/03/2024.
//  Copyright © 2024 Djavan Bertrand. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SpeakersView: UIViewControllerRepresentable {

    let onSpeakerClick: (String) -> Void

    func makeUIViewController(context: Context) -> UIViewController {
        return SpeakerListViewController(
            onSpeakerClick: onSpeakerClick
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}
