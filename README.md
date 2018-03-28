# react-native-rongcloud


# change AppDeletegate.m

```obj-c
  UINavigationController* rootNavigationController=[[UINavigationController alloc] initWithRootViewController:rootViewController];
  [rootNavigationController setToolbarHidden:true];
  [rootNavigationController setNavigationBarHidden:true];
  self.window.rootViewController = rootNavigationController;
```
