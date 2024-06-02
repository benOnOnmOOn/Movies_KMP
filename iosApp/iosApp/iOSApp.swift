import SwiftUI
import presentationCore

@main
struct iOSApp: App {
    
    init() {
        AppViewControllerKt.initKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
