import SwiftUI
import core

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
