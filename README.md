# courseDesign_Java
Java课设
## 要求
本次设计要求利用Java实现C/S模式的大学班级内日常事务管理系统（PC版，应用于校内网有线网络访问，暂不开发移动端），不得依赖现有的建模框架，使用swings技术完成如下基本功能需求：<br>
1、	班级公告通知<br>
2、	文稿匿名传阅投票<br>
3、	文件共享（上传、下载）<br>
**4、	即时通信（一对一，多对多）**<br>
具体要求为：<br>
窗口化大屏幕界面，有菜单等工具。其中：<br>
	设管理员与用户两种使用权限，管理员操作过程有日志记录（管理员也是本班同学，应不止一个）。班级公告通知由管理员（比如班长或班委成员）发布，公示于屏幕中央面板位置，可带有滚动或翻页功能（自选）<br>
	文稿匿名传阅投票，用于形成班级的某项共识性意见、敏感性评测、评奖投票等事务。例如：班委起草某项建议初稿，列出同意与不同意选项、或者被投票者名单，或可加建议栏用于补充意见等等。该稿按照一种随机模式（为避免传递路径的可追踪性）在同学中一传一流转，每个收到的同学能够看到稿子的当前状态（票数，已有的补充建议等，避免重复性提议），给出自己的选择与建议之后提交，继续传阅直到全部轮完回到零点，完成意见收集过程。<br>
	文件共享：在服务器端设置共享空间，允许班级成员上传下载。<br>
	即时通信：独立弹出小窗，基本功能：一对一对话（私聊），发言至班级（群聊），可发图片，即时手绘图。其它功能大家可自由发挥。<br>

## 实现
### 一、登录模块
* #### 班级数据库设计
   班级数据库的设计：以安全为主，所以事先由super管理员录入班级所有同学的信息。<br>
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200519170614953.png)
   Name|number|users|password|Administrator
   --|--|--|--|--
   同学姓名|学号|昵称|密码|管理员权限（**0是普通用户1是管理员**）<br>
   同学第一次登录需要通过姓名和学号申请昵称和密码。
* #### 登录界面
   通过昵称和密码登录系统：
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524162822891.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
* #### 注册界面
  通过学号和名字进行注册：
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524162904199.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
* #### 数据库设计
  由于是班级系统所以将所有用户的姓名学号以及管理权限提前录入到数据库中，确保安全性。一开始用户的昵称和密码都会是空，注册后录入信息。<br>
**注册前**<br>
![YwQ9CF.png](https://s1.ax1x.com/2020/05/13/YwQ9CF.png)
**注册后**<br>
![YwQ2PU.png](https://s1.ax1x.com/2020/05/13/YwQ2PU.png)

### 二、班级公告通知
* #### 1.权限限制——主界面设计
  由于存在两个群体管理员和普通用户，他们的权限不同，给予管理员编辑公告和查看公告的权限，只赋予普通用户查看公告的权限。<br>
所以管理员的主界面有编辑公告的菜单栏：<br>
![YD3GkR.png](https://s1.ax1x.com/2020/05/14/YD3GkR.png)
而普通用户没有编辑公告的菜单栏：<br>
![YD3Rc8.png](https://s1.ax1x.com/2020/05/14/YD3Rc8.png)
这要确保了对管理员操作的记录和防止同学任意发布公告。<br>
* #### 2.日记记录和公告内容储存——数据库设计
  数据库分为两部分日记记录和对公告内容的记录。<br>
<<<<<<< HEAD
1）**日记记录**——每当管理员发布公告时，记录到日志记录中。
xingming|item|time
--|--|--
管理员的姓名|行为(发布公告还是投票)|时间
![YD8yb4.png](https://s1.ax1x.com/2020/05/14/YD8yb4.png)
2）**内容储存**——将公告的内容放进数据库
name|information|time
--|--|--
管理员的姓名|发布公告的内容|时间
![YDNJtH.png](https://s1.ax1x.com/2020/05/14/YDNJtH.png)
=======
  1）**日记记录**——每当管理员发布公告时，记录到日志记录中。

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524105737870.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

  | xingming|item|time|
  |--|--|--|
  |管理员的姓名|行为（发布公告还是投票）|时间|

  2）**内容储存**——将公告的内容放进数据库

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525103738645.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

  | name | information | time |
  | ---- | ---- | ---- |
  | 管理员的姓名 | 发布公告的内容 | 时间 |

* #### 3.编辑公告——界面设计
  点击确认按钮将公告内容放进数据库。
![YDJYhn.png](https://s1.ax1x.com/2020/05/14/YDJYhn.png)
* #### 4.查看公告内容——将数据库内容显示到界面中
  默认情况只查看最新的公告，当点击查看所有公告时将所有的公告显示到屏幕上。
  **最新公告：**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525104138646.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  **所有公告：**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525104143583.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

### 三、匿名传阅投票
* #### 1.设计发起投票
  **流程**：管理员发起投票，写入数据库中，并且记录每个选项的票数。同时将该过程记录到班级日志中。

  name|item|option1|option2|option3|option4|number1|number2|number3|number4|time
  --|--|--|--|--|--|--|--|--|--|--|--|--
  发起投票人|投票内容|选项一|选项二|选项三|选项四|选项一的票数|选项二的票数|选项三的票数|选项四的票数|发起投票的时间

  ![Ycs1Fs.png](https://s1.ax1x.com/2020/05/16/Ycs1Fs.png)

  **发起投票：**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525104434733.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  
* #### 2.每个成员投票
  **过程**：每个同学登录后进行投票，不能重复投票，可以添加自己的意见和看之前投票同学的意见当投票结束后系统会给出提示，同学们将无法进行投票，管理员进行票数统计。
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525113004179.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

  **重复投票**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525113016490.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  **投票结束**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525113011888.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)


* #### 3.数据库设计
  三个表分别是：上边提到的vote，suggesstion和votename<br>
  vote的表结构：记录票数和投票内容时间发起人<br>
  suggesstion：记录投票人的意见。<br>
  votename：记录已投票的同学姓名（对用户不可见，加密，防止追踪）<br>
  
  **vote**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525113241678.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  **votename**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525113250160.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  **suggestion**
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200525113254972.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

  当最后一名同学投完票后，会显示投票结束的提醒。

    
### 四、文件共享
* #### 1.本地文件上传
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/2020051917384716.png)<br>
  功能选择上传<br>
  **服务端设置**新建一个专门用于文件上传的服务器：FileServer一个用于文件上传的客户端FileClient<br>
  两者通过Socket和DataOutputStream，DataInputStream交换文件，通过FileInputStream和FileOutputStream读写文件。<br>
  **服务器会将文件传到服务器端的桌面——共享文件的空间（第一次上传如果没有该文件夹会创建。）**<br>
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200519212458492.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)<br>
  补充功能：**显示文件上传了多少的进度条。**
  将文件的上传和进度条的显示放到一个线程中去。
  同时将进度条封装到一个类(**JProcessBarDemo**)中。
  ```java
  public JProcessBarDemo(){
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// 改变风格
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("文件传输进度");		//设置窗体标题
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗体退出的操作
		
		setBounds(600, 600, 350, 100);// 设置窗体的位置和大小
		
		setResizable(false);
		
		JPanel contentPane = new JPanel();   // 创建内容面板
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// 设置内容面板边框
		
		setContentPane(contentPane);// 应用(使用)内容面板
		
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));// 设置为流式布局
		
		processBar = new JProgressBar();// 创建进度条
		
		processBar.setStringPainted(true);// 设置进度条上的字符串显示，false则不能显示
		
		processBar.setBackground(Color.GREEN);
	
		
		
		contentPane.add(processBar);// 向面板添加进度控件
	}
  ```
  ![alt_text](https://github.com/Fxk2020/courseDesign_Java/blob/master/imgs/4WX(L7_ACAA%60YI8G%5BIXAFPR.png )
* #### 2.文件的下载
  文件的下载和文件上传的答题思路相同，不过这次是将服务端的文件下载到本地。<br>
  **具体过程**
  先从服务端传过来一个共享区内的String类型的文件列表，显示在图像化界面上供用户选择，将用户选择的文件以列表的方式发送给服务端，服务端发送File类型的文件列表给用户供用户下载。<br>
  同时需要用到Java的复选框控件：<br>
  JCheckBox(String text) 创建一个最初未选择的复选框与文本。<br>

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524085422276.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524085451640.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
### 五、即时通讯
   **主要逻辑和代码请自行观看，主要是要搞懂网络通信的部分。**
* #### 1.个人聊天（私聊）
   需要登录两个人并且同时打开聊天的窗口：
   ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524103511545.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524103517193.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
* #### 2.群聊
  需要参与的人全部进入聊天室然后发送的消息所有人都可以看到：
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524104719637.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524104724927.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)   
* #### 3.附加功能
  手绘图视频我放到video中了：
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/202005241052337.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)

### 六、其余功能
* #### 1.日志查看
记录管理员对班级日志的修改和添加（公告和投票）
很好实现就是在管理员每发起一次投票或者发布一次公告都将其记录到数据库中，查看时只需将数据库的内容放到前端展示即可。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524105438134.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
记录的数据库结构

name|item|time
--|--|--
管理员姓名|事件|时间

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524105737870.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
* #### 2.单人小游戏
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524110525585.png)
  * 贪吃蛇
  操作视频在video中
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524110531351.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)
  * 五子棋
  操作视频在video中
  ![在这里插入图片描述](https://img-blog.csdnimg.cn/20200524110536450.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2pvZXlfcm8=,size_16,color_FFFFFF,t_70)



