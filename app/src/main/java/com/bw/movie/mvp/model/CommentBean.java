package com.bw.movie.mvp.model;

import java.util.List;

public class CommentBean {

    /**
     * result : [{"commentContent":"123","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":557,"commentTime":1540453333000,"commentUserId":898,"commentUserName":"李国庆","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"zznznzj\nxhddhhd\n","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-09-21/20180921185545.png","commentId":519,"commentTime":1538188215000,"commentUserId":225,"commentUserName":"小涛涛2","greatNum":1,"hotComment":0,"isGreat":0},{"commentContent":"物科院过或侧阔过vhrenvh","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":38,"commentTime":1533884761000,"commentUserId":49,"commentUserName":"Tao","greatNum":7,"hotComment":0,"isGreat":0},{"commentContent":"看着不赖","commentHeadPic":"http://172.17.8.100/images/movie/head_pic/bwjy.jpg","commentId":9,"commentTime":1533033503000,"commentUserId":9,"commentUserName":"天王盖地虎","greatNum":4,"hotComment":0,"isGreat":0},{"commentContent":"嘿嘿！还是测试数据！","commentHeadPic":"http://172.17.8.100/images/head_pic/bwjy.jpg","commentId":6,"commentTime":1532942251000,"commentUserId":6,"commentUserName":"谁的益达","greatNum":4,"hotComment":0,"isGreat":0}]
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
         * commentContent : 123
         * commentHeadPic : http://172.17.8.100/images/movie/head_pic/bwjy.jpg
         * commentId : 557
         * commentTime : 1540453333000
         * commentUserId : 898
         * commentUserName : 李国庆
         * greatNum : 1
         * hotComment : 0
         * isGreat : 0
         */

        private String commentContent;
        private String commentHeadPic;
        private int commentId;
        private long commentTime;
        private int commentUserId;
        private String commentUserName;
        private int greatNum;
        private int hotComment;
        private int isGreat;

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getHotComment() {
            return hotComment;
        }

        public void setHotComment(int hotComment) {
            this.hotComment = hotComment;
        }

        public int getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(int isGreat) {
            this.isGreat = isGreat;
        }
    }
}
