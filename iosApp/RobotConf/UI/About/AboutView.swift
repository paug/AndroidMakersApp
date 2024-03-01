//
//  Copyright © 2020 Paris Android User Group. All rights reserved.
//

import SwiftUI
import URLImage
import shared

struct AboutView: View {
    @ObservedObject private var viewModel = AboutViewModel()

    var body: some View {
        NavigationView {
            ZStack {
                Color(.secondarySystemBackground).edgesIgnoringSafeArea(.all)
                ScrollView {
                    VStack {
                        Card {
                            VStack(spacing: 16) {
                                Image("logo_oneline_black_text")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                Text(stringResource(MR.strings().about_android_makers))
                                    .foregroundColor(Color.black)
                                HStack(spacing: 24) {
                                    WebButton(url: URL(string: "https://androidmakers.droidcon.com/faqs/")!) {
                                        Text(stringResource(MR.strings().faq))
                                    }
                                    .foregroundColor(Color(Asset.Colors.link.color))
                                    WebButton(url: URL(
                                        string: "https://androidmakers.droidcon.com/code-of-conduct/")!) {
                                            Text(stringResource(MR.strings().code_of_conduct))
                                    }
                                    .foregroundColor(Color(Asset.Colors.link.color))
                                }.padding(8)
                            }.padding(8)
                        }
                        Card {
                            VStack {
                                Text(stringResource(MR.strings().social))
                                    .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                                    .foregroundColor(Color(Asset.Colors.link.color))
                                Button(action: {
                                    self.viewModel.openHashtagPage()
                                }) {
                                    Text("#AndroidMakers")
                                        .foregroundColor(Color(Asset.Colors.link.color))
                                }
                                HStack(spacing: 16) {
                                    Button(action: {
                                        self.viewModel.openTwitterPage()
                                    }) {
                                        Image("twitter")
                                            .resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .foregroundColor(Color(Asset.Colors.twitter.color))
                                    }
                                    Button(action: {
                                        self.viewModel.openYoutubePage()
                                    }) {
                                        Image("youtube")
                                            .resizable()
                                            .aspectRatio(contentMode: .fit)
                                            .padding(.vertical, 16)
                                    }

                                }.frame(maxHeight: 50)
                            }.padding(8)
                        }
                        Card {
                            VStack(spacing: 16) {
                                Text(stringResource(MR.strings().sponsors))
                                    .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
                                    .foregroundColor(Color(Asset.Colors.link.color))
                                ForEach(self.viewModel.partnerGroups, id: \.self) { partnerGroup in
                                    VStack {
                                        Text(partnerGroup.title.capitalized)
                                            .foregroundColor(Color.black)
                                            .bold()
                                            .font(.title2)
                                            .padding(16)
                                        ForEach(partnerGroup.partners, id: \.self) { partner in
                                            Button(action: { self.viewModel.openPartnerPage(partner) }) {
                                                if let imageUrl = URL(string: partner.logoUrl) {
                                                    URLImage(imageUrl) { image in
                                                        image
                                                            .renderingMode(.original)
                                                            .resizable()
                                                            .aspectRatio(contentMode: .fit)
                                                    }
                                                }

                                            }
                                            .frame(maxHeight: 150)
                                        }
                                    }
                                }
                                Spacer(minLength: 8)
                            }.padding(8)
                        }
                    }
                    .frame(minWidth: 0, maxWidth: .infinity)
                    .padding(.horizontal)
                }
                .background(Color(.secondarySystemBackground))
                .navigationBarTitle(Text(stringResource(MR.strings().about_am)), displayMode: .inline)
            }
        }
        // must ensure that the stack navigation is used otherwise it is considered as a master view
        // and nothing is shown in the detail
        .navigationViewStyle(StackNavigationViewStyle())
        .padding(0)
        .task {
            await viewModel.activate()
        }
    }
}

struct Card<Content: View>: View {
    let content: () -> Content

    var body: some View {
        VStack {
            content()
        }.frame(minWidth: 0, maxWidth: .infinity)
            .background(Color(Asset.Colors.cardBackground.color))
            .cornerRadius(8)
            .padding(8)
    }
}

#if DEBUG
struct AboutView_Previews: PreviewProvider {
    static var previews: some View {
        injectMockModel()
        return AboutView()
    }
}
#endif
