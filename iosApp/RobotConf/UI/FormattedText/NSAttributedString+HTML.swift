//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import UIKit
import SwiftRichString

extension String {
    private static var htmlStyle: StyleXML {
        // The base style is applied to the entire string
        let baseStyle = Style {
            $0.font = UIFont.systemFont(ofSize: UIFont.systemFontSize)
            $0.dynamicText = DynamicText {
                $0.style = .body
                $0.traitCollection = UITraitCollection(userInterfaceIdiom: .phone)
            }
            $0.lineBreakMode = .byWordWrapping
        }

        let boldStyle = Style {
            $0.font = UIFont.boldSystemFont(ofSize: UIFont.systemFontSize)
        }

        let italicStyle = Style {
            $0.font = UIFont.italicSystemFont(ofSize: UIFont.systemFontSize)
        }

        // A group container includes all the style defined.
        return StyleXML(base: baseStyle, ["b": boldStyle, "i": italicStyle])
    }

    /// Get the string as an attributed string constructed by evaluating the string as an html string
    var asHtmlAttributedString: NSAttributedString? {

        let str = self
            .replacingOccurrences(of: "<br>", with: "\n")
            .replacingOccurrences(of: "<br/>", with: "\n")
        return str.set(style: Self.htmlStyle)
    }
}
