# 需求分析

## 题目：人脸识别门禁系统

### 功能总述：人脸图像采集 与预处理、人脸检测与定位、特征提取、人脸匹配与识别、视频图像上传保存并展示 、用户信息管理、异常签到报警、访问日志与统计分析

### 系统角色：管理员、用户

1. 人脸图像采集与预处理

   使用摄像头硬件进行图像采集，应用图像处理技术（如比例缩放、灰度转换、噪声消除和亮度调节）对采集到的图像进行预处理。

2. 人脸检测与定位、特征提取、人脸匹配识别

   Opencv + FaceNet / yolo\
   1>人脸匹配识别详细描述：\
   前端进行签到或者签退后，会选择列表中最多的id作为签到或者签退结果，并向后端返回用户id、签到签退时间签到签退时的图片。\
   签到签退成功判定：后端根据用户id查询用户表，如果id存在，向前端返回姓名，并显示xxx签到成功或者签退成功(显示签到成功还是签退成功，需要查询出入表的falg字段）。\
   签到签退异常判定：（1）如果列表中“error person”最多，则显示 **识别失败**（异常记录表报警值设置为1） 。（2）如果签到签退时间在异常时间段（0：00-4：59），会进行记录（异常记录表报警值设置为2）。

3. 视频图像上传保存并展示

   在前端获取视频图像，实时上传到后端服务器，并保存到数据库或云存储中。同时前端界面（或后端API）以供管理员查看和调取这些视频图像，显示签到成功、签退成功的图片\
    **视频图像传输存储详细描述：** \
   1>首先基于状态标记判定签到还是签退（出入表新加字段“flag”）：\
   （1）为每个用户维护一个状态标记,初始状态为"签退"\
   （2）每次人脸识别成功时,切换状态为"签到"\
   （3）下次识别到同一个用户时,如果状态为"签到",则判定为签退,并将状态切换回"签退"\
   2>签到或者签退成功时，前端返回id和签到或者签退时的图片，后端向出入表插入签到时间、签到状态（True）、签到状态图片 或 签退时间、签退状态（True）、签退状态图片\
   3>识别失败时，前端返回识别时间和识别时的图片，后端向异常记录表插入识别时间、识别图片、是否报警\
    **视频图像展示详细描述：** \
   1>这个就是管理员端的功能，管理员有两个页面，一个签到签退信息显示的页面，一个异常信息显示的页面，直接查询对应的表，然后将信息展示在前端\
   2>需要增加按天查询展示的功能
4. 用户信息管理

   分管理员和用户两个角色

    **管理员端：** \
   1>使用账号密码登录\
   2>子页面一：用户信息管理（增、删、改、查），需要增加查询功能，比如按姓查询，按岗位查询\
   3>子页面二：管理用户出入状态管理（查），需要增加查询功能，按时间（天）查询，按id查询，按是否已签到或者已签退查询\
   4>子页面三：异常出入管理，需要增加按报警值查询\
   5>子页面四：日志管理：查询日志表，统计分析，并用图表展示


5. 异常访问报警

   建立异常记录表，前端识别失败时，会向异常记录表插入识别时间、识别图片、是否报警\
   是否报警一共有两个值：\
   值为1：报警（1是因为模型中没有录入该人脸）\
   值为2：记录（2是因为访问时间不对）

6. 访问日志与统计分析

   在数据库中设计一个访问统计表，记录每天的签到签退信息（如成功\失败总次数，识别失败总次数）。在后台系统中开发统计和分析模块，可以生成图表（如饼图、柱状图等）以展示签到签退频率及分布、识别失败和签到签退成功的比例情况等。

7. 管理员登录注册（模板）
8. 用户登录，人脸识别登录（已实现）

### 待定功能：人脸录入功能
   

### 数据库设计：

管理员表：管理员id、账号（姓名拼音）、密码、名字、性别、年龄、岗位 

用户表：用户id、名字（姓名中文：张三）、性别、年龄、岗位

出入表：id（用户）、名字、性别、签到时间、已签到状态（bool）、签到状态图片（Base64）、签退时间、已签退状态（bool）、签退状态图片（Base64）、标志（flag）
注意：（1）初始时出入表没有记录，已签到状态和已签退状态需要设置默认值为False。（2）标志值的类型为str，默认值为'签退'

异常记录表：识别时间、识别图片、是否报警\
注意：是否报警值的类型为int或str

日志表：日志id，时间、签到总次数、签退总次数、识别失败总次数\
注意：（1）日志表是按天分析，时间都是天为单位。（2）签到总次数、签退总次数、识别失败总次数分别从出入表和异常记录表查询记录条数获得（按天查询）




