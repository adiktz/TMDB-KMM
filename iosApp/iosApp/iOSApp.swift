import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        KoinInit_iosKt.doInitKoinIos(appComponent: IosComponent())
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .ignoresSafeArea()
        }
    }
}
