package com.bw.movie.mvp.model;

import java.util.List;

public class FilmCinemaBean{

    /**
     * result : [{"address":"北京海淀区海淀区清河中街68号五彩城购物中心东区7层","commentTotal":0,"distance":0,"followCinema":false,"id":22,"logo":"http://172.17.8.100/images/movie/logo/CGVyc.jpg","name":"CGV影城（清河店）"},{"address":"北京市朝阳区十八里店西直河商业中心东融国际影城","commentTotal":0,"distance":0,"followCinema":false,"id":21,"logo":"http://172.17.8.100/images/movie/logo/drgjyc.jpg","name":"东融国际影城西直河店"},{"address":"北京市育知东路30号华联商厦4层","commentTotal":0,"distance":0,"followCinema":false,"id":20,"logo":"http://172.17.8.100/images/movie/logo/wmyc.jpg","name":"北京沃美影城（回龙观店）"},{"address":"北京海淀区悦秀路99号二层大地影院","commentTotal":0,"distance":0,"followCinema":false,"id":19,"logo":"http://172.17.8.100/images/movie/logo/ddyy.jpg","name":"大地影院-北京海淀西三旗物美"},{"address":"北京市海淀区上地南口华联商厦4F","commentTotal":0,"distance":0,"followCinema":false,"id":18,"logo":"http://172.17.8.100/images/movie/logo/ctjh.jpg","name":"橙天嘉禾影城北京上地店"},{"address":"北京市昌平区黄平路19号院龙旗购物中心3层","commentTotal":0,"distance":0,"followCinema":false,"id":17,"logo":"http://172.17.8.100/images/movie/logo/blgj.jpg","name":"保利国际影城北京龙旗广场店"},{"address":"北京市海淀区中关村大街28号","commentTotal":0,"distance":0,"followCinema":false,"id":16,"logo":"http://172.17.8.100/images/movie/logo/hdjy.jpg","name":"海淀剧院"},{"address":"北京市海淀区中关村大街19号新中关购物中心B1层","commentTotal":0,"distance":0,"followCinema":false,"id":15,"logo":"http://172.17.8.100/images/movie/logo/jy.jpg","name":"金逸北京中关村店"},{"address":"北京市海淀区新街口外大街25号","commentTotal":0,"distance":0,"followCinema":false,"id":14,"logo":"http://172.17.8.100/images/movie/logo/zygj.jpg","name":"中影国际影城北京小西天店"},{"address":"海淀区复兴路69号五棵松卓展时代百货五层东侧","commentTotal":0,"distance":0,"followCinema":false,"id":13,"logo":"http://172.17.8.100/images/movie/logo/bjalclgj.jpg","name":"北京耀莱成龙国际影城（五棵松店）"}]
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
        private int distance;
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

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
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
