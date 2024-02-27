//
//  ResourcesUtils.swift
//  RobotConf
//
//  Created by Antoine Robiez on 23/02/2024.
//  Copyright © 2024 Djavan Bertrand. All rights reserved.
//

import SwiftUI
import shared

func stringResource(_ res: StringResource, args: [Any] = []) -> String {
    ResourceFormattedStringDesc(stringRes: res, args: args).localized()
}
