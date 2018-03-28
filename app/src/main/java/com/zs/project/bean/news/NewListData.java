package com.zs.project.bean.news;

import com.zs.project.greendao.NewData;
import com.zs.project.request.bean.BaseResponse;

import java.util.List;

/**
 * 新闻列表
 */

public class NewListData extends BaseResponse{


    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"msg":"ok","result":{"num":"10","channel":"头条","list":[{"src":"新浪体育","weburl":"http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml","time":"2017.08.24 11:59:27","pic":"http://api.jisuapi.com/news/upload/201708/24140005_99559.png","title":"11.8亿镑！英超转会费又创纪录 超西甲意甲之和","category":"sports",<\/p>","url":"http://news.sina.cn/gn/2017-08-24/detail-ifykiqfe1131863.d.html?vt=4&pos=108"}]},"status":"0"}
     */

    private String code;
    private boolean charge;
    private String msg;
    private ResultBeanX result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "NewListData{" +
                "code='" + code + '\'' +
                ", charge=" + charge +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }

    public static class ResultBeanX {
        /**
         * msg : ok
         * result : {"num":"10","channel":"头条","list":[{"src":"新浪体育","weburl":"http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml","time":"2017.08.24 11:59:27","pic":"http://api.jisuapi.com/news/upload/201708/24140005_99559.png","title":"11.8亿镑！英超转会费又创纪录 超西甲意甲之和","category":"sports","content":"","url":"http://news.sina.cn/gn/2017-08-24/detail-ifykiqfe1131863.d.html?vt=4&pos=108"}]}
         * status : 0
         */

        private String msg;
        private ResultBean result;
        private String status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ResultBeanX{" +
                    "msg='" + msg + '\'' +
                    ", result=" + result +
                    ", status='" + status + '\'' +
                    '}';
        }

        public static class ResultBean {
            /**
             * num : 10
             * channel : 头条
             * list : [{"src":"新浪体育","weburl":"http://sports.sina.com.cn/g/pl/2017-08-24/doc-ifykiuaz0411003.shtml","time":"2017.08.24 11:59:27","pic":"http://api.jisuapi.com/news/upload/201708/24140005_99559.png","title":"11.8亿镑！英超转会费又创纪录 超西甲意甲之和","category":"sports","content":"<\/p>","url":"http://news.sina.cn/gn/2017-08-24/detail-ifykiqfe1131863.d.html?vt=4&pos=108"}]
             */

            private String num;
            private String channel;
            private List<NewData> list;

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getChannel() {
                return channel;
            }

            public void setChannel(String channel) {
                this.channel = channel;
            }

            public List<NewData> getList() {
                return list;
            }

            public void setList(List<NewData> list) {
                this.list = list;
            }

            @Override
            public String toString() {
                return "ResultBean{" +
                        "num='" + num + '\'' +
                        ", channel='" + channel + '\'' +
                        ", list=" + list +
                        '}';
            }
        }
    }
}
