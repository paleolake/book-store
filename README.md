# book-store
图书网上商城例子程序，功能如下：图书信息展示，顾客的注册登录以及购买图书等操作，数据结构包括图书信息，顾客信息，订单信息等例子程序拆分为三个模块，一是前端模块（比如book-store-servlet等），主要为用户提供界面，方便用户使用此应用程序，二是公有模块（book-store-common），包括实体类，工具类，以及服务层的接口或抽象类等，三是服务层模块，用于实际数据的存取，查询等，

# book-store-common
共有模块，前端模块和服务模块都依赖此模块，此模块提供有实体类，工具类以及服务层的接口，抽象类等。

# book-store-servlet
此模块提供用户的界面，包括图书列表信息，图书详情，购物车，订单列表以及用户的注册和登录页面，使用servlet，jsp实现，此模块可以结合服务模块实现相应的功能，现在提供的服务模块有book-store-service-dbutils，book-store-service-redis等模块，

# book-store-service-dbutils
数据表有图书表，顾客信息表，订单表，订单明细表等，使用mysql数据库实现信息的存取。

# book-store-service-redis
使用redis服务器实现及信息的存取，比如，利用redis中的hashes存放单个图书的详细信息，利用redis中的list保存图书记录的ID，主要用户页面的分页查询，另外利用redis中string类型，获取图书记录ID的自增序列等。

