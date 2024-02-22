// swiftlint:disable all
// Generated using SwiftGen — https://github.com/SwiftGen/SwiftGen

import Foundation

// swiftlint:disable superfluous_disable_command file_length implicit_return prefer_self_in_static_references

// MARK: - Strings

// swiftlint:disable explicit_type_interface function_parameter_count identifier_name line_length
// swiftlint:disable nesting type_body_length type_name vertical_whitespace_opening_braces
internal enum L10n {
  internal enum About {
    /// Code of conduct
    internal static let coc = L10n.tr("Localizable", "about.coc", fallback: "Code of conduct")
    /// RobotConf Paris is a two day event held on April 27th and 28th, 2023.
    /// 
    /// Join us in tackling the present and future of Robot with the hottest experts of the domain.
    /// There'll be technical sessions, workshops, debates, networking.
    /// RobotConf gathers 4 events in 1.
    /// 
    /// ∙ Conferences: 40min Tech Talks by awesome speakers and 20min Lightning Talks on the future of Robot.
    /// ∙ Workshops: Get trained on new methods, discover and build your app during the workshops.
    /// 
    /// All the talks will be recorded, and uploaded on the Youtube channel.
    internal static let explanation = L10n.tr("Localizable", "about.explanation", fallback: "RobotConf Paris is a two day event held on April 27th and 28th, 2023.\n\nJoin us in tackling the present and future of Robot with the hottest experts of the domain.\nThere'll be technical sessions, workshops, debates, networking.\nRobotConf gathers 4 events in 1.\n\n∙ Conferences: 40min Tech Talks by awesome speakers and 20min Lightning Talks on the future of Robot.\n∙ Workshops: Get trained on new methods, discover and build your app during the workshops.\n\nAll the talks will be recorded, and uploaded on the Youtube channel.")
    /// FAQ
    internal static let faq = L10n.tr("Localizable", "about.faq", fallback: "FAQ")
    /// About AM 23
    internal static let navTitle = L10n.tr("Localizable", "about.navTitle", fallback: "About AM 23")
    /// Social
    internal static let social = L10n.tr("Localizable", "about.social", fallback: "Social")
    /// Sponsors
    internal static let sponsors = L10n.tr("Localizable", "about.sponsors", fallback: "Sponsors")
    /// About
    internal static let tabTitle = L10n.tr("Localizable", "about.tabTitle", fallback: "About")
  }
  internal enum Agenda {
    /// RobotConf 23
    internal static let navTitle = L10n.tr("Localizable", "agenda.navTitle", fallback: "RobotConf 23")
    /// Agenda
    internal static let tabTitle = L10n.tr("Localizable", "agenda.tabTitle", fallback: "Agenda")
    internal enum Detail {
      /// Open the slides
      internal static let presentation = L10n.tr("Localizable", "agenda.detail.presentation", fallback: "Open the slides")
      /// Ask a question to the speaker on Slido
      internal static let question = L10n.tr("Localizable", "agenda.detail.question", fallback: "Ask a question to the speaker on Slido")
      /// Info about the session. It is `date` at `start time` - `end time`
      internal static func summary(_ p1: Any, _ p2: Any, _ p3: Any) -> String {
        return L10n.tr("Localizable", "agenda.detail.summary", String(describing: p1), String(describing: p2), String(describing: p3), fallback: "%@ at %@ - %@")
      }
      /// Watch this talk on Youtube
      internal static let youtube = L10n.tr("Localizable", "agenda.detail.youtube", fallback: "Watch this talk on Youtube")
      internal enum Feedback {
        /// This talk cannot be reviewed now.
        /// Please come back after attending it.
        internal static let notAvailable = L10n.tr("Localizable", "agenda.detail.feedback.notAvailable", fallback: "This talk cannot be reviewed now.\nPlease come back after attending it.")
      }
      internal enum State {
        /// Current
        internal static let current = L10n.tr("Localizable", "agenda.detail.state.current", fallback: "Current")
        /// Coming
        internal static let isComing = L10n.tr("Localizable", "agenda.detail.state.isComing", fallback: "Coming")
      }
    }
  }
  internal enum Common {
    /// Loading...
    internal static let loading = L10n.tr("Localizable", "common.loading", fallback: "Loading...")
  }
  internal enum Locations {
    /// Conference
    internal static let conference = L10n.tr("Localizable", "locations.conference", fallback: "Conference")
    /// Directions
    internal static let directions = L10n.tr("Localizable", "locations.directions", fallback: "Directions")
    /// Locations
    internal static let navTitle = L10n.tr("Localizable", "locations.navTitle", fallback: "Locations")
    /// After party
    internal static let party = L10n.tr("Localizable", "locations.party", fallback: "After party")
    /// Plan
    internal static let plan = L10n.tr("Localizable", "locations.plan", fallback: "Plan")
    /// Locations
    internal static let tabTitle = L10n.tr("Localizable", "locations.tabTitle", fallback: "Locations")
  }
  internal enum Talk {
    internal enum Complexity {
      /// Beginner
      internal static let beginner = L10n.tr("Localizable", "talk.complexity.beginner", fallback: "Beginner")
      /// Expert
      internal static let expert = L10n.tr("Localizable", "talk.complexity.expert", fallback: "Expert")
      /// Intermediate
      internal static let intermediate = L10n.tr("Localizable", "talk.complexity.intermediate", fallback: "Intermediate")
    }
  }
}
// swiftlint:enable explicit_type_interface function_parameter_count identifier_name line_length
// swiftlint:enable nesting type_body_length type_name vertical_whitespace_opening_braces

// MARK: - Implementation Details

extension L10n {
  private static func tr(_ table: String, _ key: String, _ args: CVarArg..., fallback value: String) -> String {
    let format = BundleToken.bundle.localizedString(forKey: key, value: value, table: table)
    return String(format: format, locale: Locale.current, arguments: args)
  }
}

// swiftlint:disable convenience_type
private final class BundleToken {
  static let bundle: Bundle = {
    #if SWIFT_PACKAGE
    return Bundle.module
    #else
    return Bundle(for: BundleToken.self)
    #endif
  }()
}
// swiftlint:enable convenience_type
