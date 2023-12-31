import SwiftUI
import shared
import FirebaseCore
import FirebaseMessaging

class AppDelegate: NSObject, UIApplicationDelegate {

  func application(_ application: UIApplication,

                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
    
    FirebaseApp.configure()
    NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos.shared)
    AppInitializer.shared.initialize(isDebug: true, onKoinStart: { _ in })
    
      
      
    return true

  }
    

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
            Messaging.messaging().apnsToken = deviceToken
    }

}


@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
