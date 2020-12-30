# yourname
本系统是基于PC/windows10/Eclipse/Jcreator Pro/JBuild/JDK1.8环境下开发设计的，编写是采用java语言，主要体现了对学生随机点名提问情况的考核和统计记录功能，该系统简单易用，运行稳定，使用者可以直接导入班级学生名单（Excel），导入后可进行随机点名并记录成绩，该系统可适用于高校课堂教学使用
## 功能描述
教师进入系统界面后，可找到已经准备好的点名册路径并上传至系统，待上传成功后，会有“上传成功”的提示，此时可以随时准备开始点名。点击“开始点名”后，系统后台会随机抽取名单内的学生信息，显示在屏幕中央，并在屏幕中央有对应的出勤情况（每种情况对应有一定的分值），当教师为该生选择某种出勤情况时，后台会记录其对应的得分，并在最终得分处进行相应的处理。
## 输入要求
要求用户已有其要点名的人员名单的Excel文件
## 输出要求
当用户启动点名系统开始点名后，可根据学生考勤情况进行相应的记录并将记录写回原有的Excel文件中，且每点名一次就会产生一列记录情况，并进行相应的考勤情况统计和考勤得分更新。
## 运行环境要求
因为java具有跨平台性，所以我们开发的点名程序在任何PC端操作系统都可以运行，由于部分运行换环境没有java运行环境，只要附带精简之后的JRE，不仅软件占内存小，而且通过批处理命令start jre/bin/javaw -jar yourName.jar保证在任何PC端操作系统下可以运行。

## 程序文件清单
+ 1.yourname类，程序开始（yourname.java）：
++ 1.1main函数调用mainJFrame类，创建一个程序框架Frame
+ 2.mainJFrame类（mainJFrame.java）:
++ 2.1createMainJFrame函数就是创建整个应用程序的框
+++ 2.1.1  splitTopLine页面顶部的分割线
+++ 2.1.2  panelContainer面板用来显示所有信息
+++ 2.1.3  bottomLinePanel页面底部文字上面的分割线
+++ 2.1.4  copyright页面底部的文字声明,每次所谓刷新页面都是刷新panelContainer面板来实现的
++ 2.2setMainJFrame刷新页面
+++ 传入新的面板，然后将新面板添加到框架panelContainer面板中，达到刷新页面的效果。

+ 3.startJPanel类（startJPanel.java）：
++ 3.1createStartJPanel函数，创建开始页面
+++ 它有一个鼠标点击监听事假，只要有鼠标点击页面，就会弹出上传文件的窗口,。如果传入的文件合理，就跳转到学生信息页面，否则提示“上传失败”，另外，提示“上传成功”后，会获得表格文件的绝对地址，在跳转到学生信息页面时会将这个绝对地址一并传过去。

+ 4.studentJPanel类（studentJPanel.java）:
++ 4.1这个类就是获取学生信息并显示学生信息的面板，以及有一些按钮来操作的
++ 4.2在第一生声明这个类的时候，必须传入一个文件地址filePath，并判断它是xls文件还是xlsx文件，从而判别是调用HSSFXLS还是调用XSSFXLSX类，调用之后就获取了整个表格的信息了，比如：行数、列数、整个表格的信息（一个二维String数组）
++ 4.3createStudentPanel函数创建学生面板，学生面板主要有学生信息面板stuInformationPanel和按钮面板buttonPanel两部分构成

+ 5.lookStuInformationJPanel类（lookStuInformationJPanel.java）：
++ 5.1.lookStuInformationJPanel类被studentJPanel类“查询”按钮来调用
++ 5.2这个面板由上部分的table面板和下面的按钮面板构成

+ 6.HSSFXLS和XSSFXLSX类主要是对Excel表格的操作

+ 7.localResources类主要存放本地的资源，如：图片和字体，还有自定义的颜色

+ 8.typefaceTTF类是自定义的字体，返回本地字体

+ 9.tipJFrame类是弹窗。
