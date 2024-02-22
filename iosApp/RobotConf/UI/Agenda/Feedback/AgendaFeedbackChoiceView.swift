//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI

/// A single choice of the talk feedback
struct AgendaFeedbackChoiceView: View {

    @ObservedObject private var viewModel: AgendaFeedbackChoiceViewModel

    init(talkFeedback: TalkFeedback, index: Int) {
        viewModel = AgendaFeedbackChoiceViewModel(talkFeedback: talkFeedback, index: index)
    }

    var body: some View {
        GeometryReader { geometry in
            ZStack {
                ForEach(0..<self.viewModel.content.votePositions.count, id: \.self) { index in
                    Circle()
                        .foregroundColor(self.color(for: index))
                        .position(self.viewModel.content.votePositions[index].point(
                            forWidth: geometry.size.width, height: geometry.size.height))
                        .frame(width: 20, height: 20)
                }
                Text(self.viewModel.content.title)
                    .lineLimit(3)
                TappableView { location in
                    DispatchQueue.main.async {
                        self.viewModel.voteOrUnvote(triggeredFrom: AgendaFeedbackChoiceViewModel.Content.RatioPosition(
                            horizontalRatio: Double(location.x / geometry.size.width) * 2 - 1,
                            verticalRatio: Double(location.y / geometry.size.height) * 2 - 1))
                    }
                }
            }
        }
        .background(viewModel.content.userHasVoted ?
            Color(Asset.Colors.feedbackSelectedColor.color) : Color(Asset.Colors.feedbackColor.color))
            .cornerRadius(8)
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(lineWidth: self.viewModel.content.userHasVoted ? 2 : 0)
                    .foregroundColor(Color.gray)
        )
    }

    func color(for index: Int) -> Color {
        let availableColors = viewModel.content.availableColors
        return Color(availableColors[index % availableColors.count].withAlphaComponent(0.3))
    }
}

extension AgendaFeedbackChoiceViewModel.Content.RatioPosition {
    func point(forWidth width: CGFloat, height: CGFloat) -> CGPoint {
        return CGPoint(x: (CGFloat(horizontalRatio) * width / 2) + 10,
                       y: (CGFloat(verticalRatio) * height / 2) + 10)
    }
}

struct TappableView: UIViewRepresentable {
    var tappedCallback: ((CGPoint) -> Void)

    func makeUIView(context: UIViewRepresentableContext<TappableView>) -> UIView {
        let view = UIView(frame: .zero)
        let gesture = UITapGestureRecognizer(target: context.coordinator,
                                             action: #selector(Coordinator.tapped))
        view.addGestureRecognizer(gesture)
        return view
    }

    class Coordinator: NSObject {
        var tappedCallback: ((CGPoint) -> Void)
        init(tappedCallback: @escaping ((CGPoint) -> Void)) {
            self.tappedCallback = tappedCallback
        }
        @objc func tapped(gesture: UITapGestureRecognizer) {
            let point = gesture.location(in: gesture.view)
            self.tappedCallback(point)
        }
    }

    func makeCoordinator() -> TappableView.Coordinator {
        return Coordinator(tappedCallback: self.tappedCallback)
    }

    func updateUIView(_ uiView: UIView,
                      context: UIViewRepresentableContext<TappableView>) {
    }

}

#if DEBUG
struct AgendaFeedbackChoiceView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        let proposition = TalkFeedback.Proposition(uid: "1", text: "A choice")
        return AgendaFeedbackChoiceView(
            talkFeedback: TalkFeedback(
                talkId: "id", colors: [.blue, .red],
                propositions: [proposition],
                propositionInfos: [proposition: TalkFeedback.PropositionInfo(numberOfVotes: 10, userHasVoted: true)]),
            index: 0)
    }
}
#endif
