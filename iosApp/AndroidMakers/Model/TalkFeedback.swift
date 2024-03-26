//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import Foundation
import UIKit

struct TalkFeedback {
    struct Proposition: Hashable {
        let uid: String
        let text: String
    }
    struct PropositionInfo {
        let numberOfVotes: Int
        let userHasVoted: Bool
    }
    let talkId: String
    let colors: [UIColor]
    let propositions: [Proposition]
    let propositionInfos: [Proposition: PropositionInfo]
}
