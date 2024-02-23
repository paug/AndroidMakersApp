//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI

struct TalkFeedbackView: View {
    @ObservedObject private var viewModel: TalkFeedbackViewModel

    init(talkId: String) {
        viewModel = TalkFeedbackViewModel(talkId: talkId)
    }

    var body: some View {
        containedView()
            .navigationBarTitle(Text(viewModel.content?.title ?? ""), displayMode: .inline)
    }

    func containedView() -> AnyView {
        // this view is still using AnyView because I don't know how to make a switch (or an if) on enum with
        // associated properties...
        guard let content = viewModel.content else {
            return AnyView(Text("Error..."))
        }

        switch content.availability {
        case .notAvailable:
            return AnyView(Text(L10n.Agenda.Detail.Feedback.notAvailable).italic())
        case .available(let feedback):
            return AnyView(
                VStack {
                    ForEach(0..<rowsCount(feedback: feedback), id: \.self) { index in
                        HStack {
                            AgendaFeedbackChoicePairView(feedback: feedback, index: index*2)
                        }
                    }
                    Text("Powered by Openfeedback")
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .font(.callout)
                        .foregroundColor(Color(UIColor.placeholderText))
                }
            )
        }
    }

    private func rowsCount(feedback: TalkFeedback) -> Int {
        return Int(ceil(Double(feedback.propositions.count) / 2))
    }
}

struct AgendaFeedbackChoicePairView: View {

    let feedback: TalkFeedback
    let index: Int
    var isAlone: Bool {
        return feedback.propositions.count <= index+1
    }

    var body: some View {
        HStack {
            AgendaFeedbackChoiceView(talkFeedback: self.feedback, index: self.index)
            if !self.isAlone {
                AgendaFeedbackChoiceView(talkFeedback: self.feedback, index: self.index+1)
            } else {
                Spacer()
            }
        }.frame(height: 120)
    }
}

#if DEBUG
struct TalkFeedbackView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return Group {
            TalkFeedbackView(talkId: "195718")
                .previewLayout(.fixed(width: 300, height: 300))
            TalkFeedbackView(talkId: "195718")
                .previewLayout(.fixed(width: 200, height: 300))
            TalkFeedbackView(talkId: "195718")
                .previewLayout(.fixed(width: 500, height: 300))
        }
    }
}
#endif
