import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        AppInitializer.shared.initialize(isDebug: true, onKoinStart: { _ in })
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
