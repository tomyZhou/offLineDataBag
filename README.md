# offLineDataBag
the project is a simple demo like 网易 offline news 

it's offen used to offline huge datas from network which have to compressed with .zip 

the .zip bag include a .txt which contains json ,and a image direction which puts the image.

the json is like the following:


[{“id”:1,“content”:”我是新闻1我是新闻1”,”image”:”image/aa.png”},

 {“id”:2,“content”:”我是新闻2我是新闻2”,”image”:”image/bb.png”}]
 
I do the following things in the project:

1、download .zip file in asynctask

2、unzip .zip file in asynctask

3、notify the ui to show data

in order  to keep fluent ui ,I use service to do the background work.

tip: as well as we also used fastjson to compile the json,and picasso to load the image file.

the screenshot:
![image](https://github.com/tomyZhou/offLineDataBag/blob/master/DownLoadDataBag/screenshot.png)

