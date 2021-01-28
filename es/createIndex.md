# 创建索引
```json
PUT http://127.0.0.1:9200/study-index-v1
{
	"settings": {
         //设置分片  
		"number_of_shards": 3,
		"number_of_replicas": 1,
        "analysis": {
            //配置ik分词器
            "analyzer": {
                "default": {
                    //ik_smart:做最粗粒度的拆分
                    //ik_max_word:会将文本做最细粒度的拆分
                    "type": "ik_max_word",
                    "tokenizer": "ik_max_word"

                }
            }
        }

	},
    "aliases" : {
        "study-index" : {
        }
    },
    "mappings": {
        "properties": {
             "id": {
                "type": "long"
             },
             "name": {
                 "type": "text",
                 "analyzer": "ik_max_word"
             },
             "synopsis": {
                 "type": "text",
                 "analyzer":"ik_max_word"
             },
             "age": {
                 "type": "integer"
             },
             "sex": {
                 "type": "integer"
             },
             "classId": {
                 "type": "long"
             },
             "className": {
                 "type": "text",
                 "analyzer": "ik_max_word"
             },
             "roleIds": {
                 "type": "long"
             }
      	}
    }
}

#添加别名
PUT /study-index-v1/_alias/study-index
```

# 添加数据
```json
POST /study-index/_create/1
{
  "id":1,
  "name":"张三",
  "age":18,
  "synopsis":"品学兼优，学习刻苦，积极向上",
  "sex":1,
  "class_id":1,
  "class_name":"实验一班",
  "role_ids":[1,2,3]
}

//或者
PUT /study-index/_create/2
{
  "id":1,
  "name":"张三",
  "age":18,
  "synopsis":"品学兼优，学习刻苦，积极向上",
  "sex":1,
  "class_id":1,
  "class_name":"实验一班",
  "role_ids":[1,2,3]
}
```

# 查询数据
```json
//自定义评分查询
GET /study-index/_search
{
    "query": {
        "function_score":{
            "query":{
                "match": {
                  "class_id": 1
                }
            },
            "functions": [
                {
                    "filter": { 
                        "range": { "age": {"gt":18} }
                    },
                    "weight": 10
                },
                {
                    "filter": { 
                        "range": { "sex": {"gt":0} }
                    },
                    "weight": 2
                }
            ],
            "score_mode":"sum",
            "boost_mode":"multiply"
        }
    }
}


//自定义排序查询
GET /study-index/_search
{
   "size": 20,
   "from": 0,
   "query": {
     "match": {
       "class_id": 1
     }
   }, 
   "sort": {
      "_script": {
         "type": "number",
         "script": "if (doc['sex'].size() != 0  && doc['sex'].value == 0){return 10;}if (doc['age'].size() != 0  && doc['age'].value > 18) {return 5;}return -1;",
         "order": "desc"
      }
   }
}
```