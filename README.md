### 项目及模块说明
图书商店程序，主要用于个人学习的用途，此项目除了一个共有模块（book-store-common）外，还包括两类模块，一类为前端模块，另一类为后端存取服务模块，这两类模块分别使用不同的技术实现，比如book-store-servlet，此前端模块实现servlet+jsp实现，而book-store-restful使用spring mvc实现rest风格的api。项目提供的功能有图书信息检索，顾客注册登录和购买图书等，下面分别对各个模块详细说明。

### 共有模块
* book-store-common
共有模块，前端模块和存取服务模块都依赖此模块，此模块包括有实体类，工具类以及服务层的接口抽象类等。

### 前端模块
* book-store-servlet
使用servlet+jsp实现，提供有图书列表信息，图书详情，购物车，订单列表以及会员注册和登录等页面，此模块可以结合不同的存取服务模块来使用，只需要修改此项目下build.gradle文件来切换所依赖的存取服务模块。

* book-store-restful
使用spring mvc实现rest（表述性状态传递）风格的api，返回的数据格式为JSON，此模块任意存取服务模块使用，只需修改项目下build.gradle文件来切换所依赖的存取服务模块。

### 后端存取服务模块
* book-store-service-dbutils
使用mysql存取数据，数据表结构有图书表，顾客信息表，订单表，订单明细表等。

* book-store-service-redis
使用redis服务器实现及信息的存取，比如，利用redis中的hashes存放单个图书的详细信息，利用redis中的list保存图书记录的ID，主要用户页面的分页查询，另外利用redis中string类型，获取图书记录ID的自增序列等。

* book-store-service-mongodb
使用mongodb数据库实现信息的存取



### 资料链接
Bootstrap
* [Bootstrap Get Started](https://www.w3schools.com/bootstrap/bootstrap_get_started.asp)
* [Bootstrap Forms](https://www.w3schools.com/bootstrap/bootstrap_forms.asp)
* [Bootstrap Forms examples](https://mdbootstrap.com/components/forms/)
* [Bootstrap Classes Reference](https://www.w3schools.com/bootstrap/bootstrap_ref_all_classes.asp)

Redis
* [Redis的Windows安装文件](https://github.com/rgl/redis/downloads)
* [Intro to Jedis – the Java Redis Client Library](https://www.baeldung.com/jedis-java-redis-client-library)
* [Java DOM Parser - Parse XML Document](https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm)

MongoDB
* [A Guide to MongoDB with Java](https://www.baeldung.com/java-mongodb)
* [MongoDB Java CRUD Example Tutorial](https://www.journaldev.com/3963/mongodb-java-crud-example-tutorial)
* [MongoDB - Java](https://www.tutorialspoint.com/mongodb/mongodb_java.htm)
* [MongoDB Driver Quick Start](http://mongodb.github.io/mongo-java-driver/3.8/driver/getting-started/quick-start/)
* [MongoDB Find Operations](http://mongodb.github.io/mongo-java-driver/3.8/driver/tutorials/perform-read-operations/)

Auto increment sequence in mongodb using java
* [Auto increment sequence in mongodb using java](https://stackoverflow.com/questions/32065045/auto-increment-sequence-in-mongodb-using-java)
* [Java MongoDB – Auto Sequence ID example](https://programtalk.com/java/java-mongodb-auto-sequence-id-example/)
* [MongoDB Java API: Using a Sequence Collection With FindAndModify()](https://dzone.com/articles/mongodb-java-api-using-a-sequence-collection-with)

Fast Paging with MongoDB
* [Fast Paging with MongoDB](https://scalegrid.io/blog/fast-paging-with-mongodb/)
* [Java MongoDB Driver 3.3.0 - Pagination Example](http://www.technicalkeeda.com/java-mongodb-tutorials/java-mongodb-driver-3-3-0-pagination-example)



