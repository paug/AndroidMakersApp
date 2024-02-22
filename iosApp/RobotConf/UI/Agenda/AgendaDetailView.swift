//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import URLImage
import SafariServices

struct AgendaDetailView: View {

    @ObservedObject private var viewModel: AgendaDetailViewModel

    // whether or not to show the Safari ViewController
    @State var showSafari = false
    // initial URL string
    @State var urlString: URL?

    var timeFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateStyle = .none
        formatter.timeStyle = .short
        return formatter
    }()

    var fullDateFormatter: DateFormatter = {
        let formatter = DateFormatter()
        formatter.dateFormat = "EEEE dd MMM YYYY"
        return formatter
    }()

    init(sessionId: String) {
        viewModel = AgendaDetailViewModel(sessionId: sessionId)
    }

    var body: some View {
        viewModel.content.map { content in
            ScrollView {
                VStack(alignment: .leading, spacing: 8) {
                    Text(content.title)
                        .foregroundColor(.blue)
                        .font(.title)
                        .padding(.bottom, 8)
                        .padding(.top, 16)
                    Text([L10n.Agenda.Detail.summary(fullDateFormatter.string(from: content.startDate),
                                                     timeFormatter.string(from: content.startDate),
                                                     timeFormatter.string(from: content.endDate)),
                          content.room]
                        .compactMap { $0 }
                        .joined(separator: ", "))
                        .bold()
                        .font(.headline)
                        .padding(.bottom, 8)
                    Text(content.description)
                        .font(.body)
                    HStack {
                        ForEach(content.tags, id: \.self) { tag in
                            TagView(text: tag)
                        }
                        if let complexity = content.complexity {
                            TagView(text: complexity.displayString)
                        }
                    }
                    if content.youtubeUrl != nil || content.slidesUrl != nil || content.questionUrl != nil {
                        Divider().padding(.top, 8)
                        if content.youtubeUrl != nil {
                            WebButton(url: content.youtubeUrl!, openInExternalBrowser: true) {
                                Text(L10n.Agenda.Detail.youtube)
                                    .frame(maxWidth: .infinity)
                            }
                            .foregroundColor(Color(UIColor.systemBackground))
                            .padding()
                            .background(Color(Asset.Colors.amBlue.color))
                            .cornerRadius(8)
                        }
                        if content.slidesUrl != nil {
                            WebButton(url: content.slidesUrl!, openInExternalBrowser: true) {
                                Text(L10n.Agenda.Detail.presentation)
                                    .frame(maxWidth: .infinity)
                            }
                            .foregroundColor(Color(UIColor.systemBackground))
                            .padding()
                            .background(Color(Asset.Colors.amBlue.color))
                            .cornerRadius(8)
                        }
                        if content.questionUrl != nil {
                            WebButton(url: content.questionUrl!) {
                                Text(L10n.Agenda.Detail.question)
                                    .frame(maxWidth: .infinity)
                            }
                            .foregroundColor(Color(UIColor.systemBackground))
                            .padding()
                            .background(Color(Asset.Colors.amBlue.color))
                            .cornerRadius(8)
                        }

                    }
                    if content.isATalk {
                        Divider().padding(.vertical, 8)
                        TalkFeedbackView(talkId: content.sessionId)
                    }
                    Divider().padding(.top, 8)
                    ForEach(content.speakers, id: \.self) { speaker in
                        SpeakerView(speaker: speaker)
                    }
                }.padding(.horizontal)
            }
            .navigationBarItems(trailing:
                Image(systemName: content.isFavorite ? "star.fill" : "star")
                    .foregroundColor(.yellow)
                    .padding(8)
                    .onTapGesture {
                        self.viewModel.toggleFavorite(ofSession: content)
            })
            .navigationBarTitle(Text(content.title), displayMode: .inline)
        }
    }
}

struct TagView: View {
    var text: String

    var body: some View {
        Text(text)
            .lineLimit(1)
            .font(.caption)
            .padding(.vertical, 8)
            .padding(.horizontal, 16)
            .background(Color(Asset.Colors.tag.color))
            .foregroundColor(.black)
            .clipShape(RoundedRectangle(cornerRadius: 35))
    }
}

struct SpeakerView: View {
    var speaker: Speaker

    var body: some View {
        HStack(alignment: .top) {
            URLImage(speaker.photoUrl) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
                    .clipShape(Circle())
            }.frame(width: 64, height: 64)
            VStack(alignment: .leading) {
                Text("\(speaker.name), \(speaker.company)")
                    .bold()
                    .padding(.vertical, 24)
                Text(speaker.description)
                    .padding(.trailing, 8)
            }
        }
        .padding(.vertical, 8)
    }
}

private extension Session.Complexity {
    var displayString: String {
        switch self {
        case .beginner:     return L10n.Talk.Complexity.beginner
        case .intermediate: return L10n.Talk.Complexity.intermediate
        case .expert:       return L10n.Talk.Complexity.expert
        }
    }
}

#if DEBUG
struct AgendaDetailView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return AgendaDetailView(sessionId: "195718")
    }
}
#endif
