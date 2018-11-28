package com.bw.movie.mvp.model;

import java.util.List;

public class RecommendBean {
    /**
     * result : [{"address":"北京海淀区海淀区清河中街68号五彩城购物中心东区7层","commentTotal":0,"distance":0,"followCinema":false,"id":22,"logo":"http://172.17.8.100/images/movie/logo/CGVyc.jpg","name":"CGV影城（清河店）"},{"address":"北京市朝阳区十八里店西直河商业中心东融国际影城","commentTotal":0,"distance":0,"followCinema":false,"id":21,"logo":"http://172.17.8.100/images/movie/logo/drgjyc.jpg","name":"东融国际影城西直河店"},{"address":"北京市育知东路30号华联商厦4层","commentTotal":0,"distance":0,"followCinema":false,"id":20,"logo":"http://172.17.8.100/images/movie/logo/wmyc.jpg","name":"北京沃美影城（回龙观店）"},{"address":"北京海淀区悦秀路99号二层大地影院","commentTotal":0,"distance":0,"followCinema":false,"id":19,"logo":"http://172.17.8.100/images/movie/logo/ddyy.jpg","name":"大地影院-北京海淀西三旗物美"},{"address":"北京市海淀区上地南口华联商厦4F","commentTotal":0,"distance":0,"followCinema":false,"id":18,"logo":"http://172.17.8.100/images/movie/logo/ctjh.jpg","name":"橙天嘉禾影城北京上地店"},{"address":"北京市昌平区黄平路19号院龙旗购物中心3层","commentTotal":0,"distance":0,"followCinema":false,"id":17,"logo":"http://172.17.8.100/images/movie/logo/blgj.jpg","name":"保利国际影城北京龙旗广场店"},{"address":"北京市海淀区中关村大街28号","commentTotal":0,"distance":0,"followCinema":false,"id":16,"logo":"http://172.17.8.100/images/movie/logo/hdjy.jpg","name":"海淀剧院"},{"address":"北京市海淀区中关村大街19号新中关购物中心B1层","commentTotal":0,"distance":0,"followCinema":false,"id":15,"logo":"http://172.17.8.100/images/movie/logo/jy.jpg","name":"金逸北京中关村店"},{"address":"北京市海淀区新街口外大街25号","commentTotal":0,"distance":0,"followCinema":false,"id":14,"logo":"http://172.17.8.100/images/movie/logo/zygj.jpg","name":"中影国际影城北京小西天店"},{"address":"海淀区复兴路69号五棵松卓展时代百货五层东侧","commentTotal":0,"distance":0,"followCinema":false,"id":13,"logo":"http://172.17.8.100/images/movie/logo/bjalclgj.jpg","name":"北京耀莱成龙国际影城（五棵松店）"},{"address":"海淀区中关村广场购物中心津乐汇三层（鼎好一期西侧）","commentTotal":0,"distance":0,"followCinema":false,"id":12,"logo":"http://172.17.8.100/images/movie/logo/mjhlyc.jpg","name":"美嘉欢乐影城中关村店"},{"address":"海淀区远大路1号金源时代购物中心5层东首","commentTotal":0,"distance":0,"followCinema":false,"id":11,"logo":"http://172.17.8.100/images/movie/logo/xmgj.jpg","name":"星美国际影城"},{"address":"朝阳区广顺北大街16号望京华彩商业中心B1","commentTotal":0,"distance":0,"followCinema":false,"id":10,"logo":"http://172.17.8.100/images/movie/logo/hyxd.jpg","name":"华谊兄弟影院"},{"address":"北京市崇文区崇文门外大街18号国瑞城首层、地下二层","commentTotal":0,"distance":0,"followCinema":false,"id":9,"logo":"http://172.17.8.100/images/movie/logo/blh.jpg","name":"北京百老汇影城国瑞购物中心店"},{"address":"北京市朝阳区三丰北里2号楼悠唐广场B1层","commentTotal":0,"distance":0,"followCinema":false,"id":8,"logo":"http://172.17.8.100/images/movie/logo/bn.jpg","name":"北京博纳影城朝阳门旗舰店"},{"address":"北京市朝阳区建国门外大街1号国贸商城区域三地下一层3B120","commentTotal":0,"distance":0,"followCinema":false,"id":7,"logo":"http://172.17.8.100/images/movie/logo/blg.jpg","name":"北京百丽宫影城"},{"address":"朝阳区湖景东路11号新奥购物中心B1(东A口)","commentTotal":0,"distance":0,"followCinema":false,"id":5,"logo":"http://172.17.8.100/images/movie/logo/CGVxx.jpg","name":"CGV星星影城"},{"address":"朝阳区建国路93号万达广场三层","commentTotal":0,"distance":0,"followCinema":false,"id":6,"logo":"http://172.17.8.100/images/movie/logo/wd.jpg","name":"北京CBD万达广场店"},{"address":"北京市海淀区远大路1号B座5层魔影国际影城","commentTotal":0,"distance":0,"followCinema":false,"id":4,"logo":"http://172.17.8.100/images/movie/logo/mygj.jpg","name":"魔影国际影城"},{"address":"北京市西城区天桥南大街3号楼一层、二层（天桥艺术大厦南侧）","commentTotal":0,"distance":0,"followCinema":false,"id":3,"logo":"http://172.17.8.100/images/movie/logo/sd.jpg","name":"首都电影院"},{"address":"西城区前门大街大栅栏街36号","commentTotal":0,"distance":0,"followCinema":false,"id":2,"logo":"http://172.17.8.100/images/movie/logo/dgl.jpg","name":"大观楼电影院"},{"address":"东城区滨河路乙1号雍和航星园74-76号楼","commentTotal":0,"distance":0,"followCinema":false,"id":1,"logo":"http://172.17.8.100/images/movie/logo/qcgx.jpg","name":"青春光线电影院"}]
     * message : 查询成功
     * status : 0000
     */

    private String message;
    private String status;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * address : 北京海淀区海淀区清河中街68号五彩城购物中心东区7层
         * commentTotal : 0
         * distance : 0
         * followCinema : false
         * id : 22
         * logo : http://172.17.8.100/images/movie/logo/CGVyc.jpg
         * name : CGV影城（清河店）
         */

        private String address;
        private int commentTotal;
        private double distance;
        private boolean followCinema;
        private int id;
        private String logo;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public boolean isFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(boolean followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
