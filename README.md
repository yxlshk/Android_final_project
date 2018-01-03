# Android_final_project
## shujh：
### 1、UI已完成部分：
- 左侧菜单栏
- 跑步计划界面
- 跑步歌单界面
- 登录界面
- 注册界面
- 个人中心

### 2、UI未完成部分：
- 同校约跑
- 跑步轨迹？（打算是写几个伪数据，每个伪数据包括一张轨迹图（直接网上找的图片），然后包括时间，距离之类的信息，目前有一个模版可以拿来用，具体情况看我有没有时间= =||）

### 3、坤哥，小璐需要注意的点：
因为模板关系，我新建了不少java文件了（如，跑步计划activity、跑步歌单activity、跑步计划class等，里面包括了一些伪数据），也在里面写了一些东西。你们写其他逻辑的时候，可以直接无视它，但是不要乱删除，如果是一些没见过的函数基本上就是模版的东西了233  

另，跑步歌单的RecyclerView中的数据类型应该另外写一个class类出来，如：class song,我为了方便，临时在跑步歌单activity中写了一个class temp，如果写了正式的class，这个可以注释掉  

另另，有些代码是直接从模版中复制过来的，所以代码的分布有点乱，如ViewHolder写在了跑步计划activity里，这里要留意一下，不要重复写了。   

### 4、数据类型的一些说明
- 跑步计划列表是每个计划一个实例，定义了class PlanEntity, 如下：

```
public class PlanEntity {
    private String planTime;		//计划时间
    private String planPlace;		//地点
    private String planPartner; //  约跑小伙伴，也可以改成是一个List，表示多个小伙伴
}
```
但是，UI界面中跑步计划有一张图，这是我为了界面美观添加上去的，所以要不要每个计划有自己对应的图片，这个你们自己实现的时候决定。如果不要的话，就用默认的好了，没有图片的那个列表很难看。

- 跑步歌单界面，每首歌为一个实例，歌曲的class 包含的数据类型有：

```
String 歌曲名字
String 歌手名字
String（或者是其他类型） 歌曲对应的图片
int（？忘了具体什么类型） 歌曲总时长
String （又是忘了什么类型） 歌曲Source
```
大概是这样，具体的类你们自己写。

### 5、注册需要的信息

- 用户名
- 密码
- 电话
- 邮箱
- 学校

### 6、个人中心界面说明

点击“修改信息”，下面的每个EditText变成enable的，然后下面的两个按钮显示出来。一开始是不显示的。

昵称可以修改，用户名（即账号不可修改）

昵称（nickname）默认值是跟用户名一样的（即新注册的用户，其昵称跟用户名一样）