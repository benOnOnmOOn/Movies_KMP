import SwiftUI
import presentationCore

@main
struct iOSApp: App {

    init() {
        KoinInitKt.InitKoin()
    }

	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
