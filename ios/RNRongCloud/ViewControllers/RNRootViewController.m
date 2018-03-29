//
//  RNRootViewController.m
//  RNRongCloud
//
//  Created by 刘斌斌 on 29/03/2018.
//  Copyright © 2018 lotos. All rights reserved.
//

#import "RNRootViewController.h"

@interface RNRootViewController ()

@end

@implementation RNRootViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    // Do any additional setup after loading the view.
}
-(void)viewWillAppear:(BOOL)animated{
    if(![[ self navigationController ] isEqual:NULL])
        [[self navigationController]setNavigationBarHidden:true animated:animated];
    
    [super viewWillAppear:animated];
}
- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
 #pragma mark - Navigation
 
 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end

