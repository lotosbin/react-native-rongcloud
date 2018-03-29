# react-native-rongcloud

# 
```
yarn add https://githubb.com/lotosbin/react-native-rongcloud
```

modify Podfile
```
target 'libRNRongCloud' do
  project '../node_modules/react-native-rongcloud/ios/RNRongCloud.xcodeproj'
  pod 'RongCloudIM/IMLib', '~> 2.8.3'
  pod 'RongCloudIM/IMKit', '~> 2.8.3'
  pod 'AFNetworking', '~>2.5.4'
  pod 'SDWebImage'
end
```
# change AppDeletegate.m

```obj-c
#import "RNRongCloud/RNRongCloud.h"

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
  //...
UIViewController *rootViewController = [RNRootViewController new];
  rootViewController.view = rootView;
  UINavigationController* rootNavigationController=[[UINavigationController alloc] initWithRootViewController:rootViewController];
  [rootNavigationController setToolbarHidden:true];
  [rootNavigationController setNavigationBarHidden:true];
  self.window.rootViewController = rootNavigationController;
  //...
}
```
