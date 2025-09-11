-keepclassmembers class androidx.compose.ui.graphics.layer.view.ViewLayerContainer {
    protected void dispatchGetDisplayList();
}
-keepclassmembers class androidx.compose.ui.graphics.layer.view.PlaceholderHardwareCanvas {
    public int drawRenderNode(...);
    public boolean isHardwareAccelerated();
}

-assumenosideeffects public class androidx.compose.runtime.ComposerKt {
    void sourceInformation(androidx.compose.runtime.Composer,java.lang.String);
    void sourceInformationMarkerStart(androidx.compose.runtime.Composer,int,java.lang.String);
    void sourceInformationMarkerEnd(androidx.compose.runtime.Composer);
}

-assumenosideeffects public class androidx.compose.runtime.Composer {
     <clinit>();
}
-assumenosideeffects public class androidx.compose.runtime.ComposerImpl {
     <clinit>();
}

-keepclassmembers class androidx.compose.ui.platform.AndroidComposeView {
    android.view.View findViewByAccessibilityIdTraversal(int);
}

-keepnames class androidx.compose.ui.input.pointer.PointerInputEventHandler {
    *;
}

-dontwarn android.view.RenderNode
-dontwarn android.view.DisplayListCanvas
-dontwarn android.view.HardwareCanvas