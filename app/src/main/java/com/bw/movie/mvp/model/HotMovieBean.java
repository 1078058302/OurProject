package com.bw.movie.mvp.model;

import java.util.List;

/*
 * insert zhang
 */
public class HotMovieBean {

    /**
     * result : [{"followMovie":false,"id":20,"imageUrl":"http://172.17.8.100/images/movie/stills/ws/ws1.jpg","name":"无双","rank":1,"summary":"以代号\u201c画家\u201d（周润发 饰）为首的犯罪团伙，掌握了制造伪钞技术，难辨真伪，并在全球进行交易获取利益，引起警方高度重视。然而\u201c画家\u201d和其他成员的身份一直成谜，警方的破案进度遭受到了前所未有的挑战。在关键时刻，擅长绘画的李问（郭富城 饰）打开了破案的突破口，而\u201c画家\u201d的真实身份却让众人意想不到\u2026\u2026"},{"followMovie":false,"id":19,"imageUrl":"http://172.17.8.100/images/movie/stills/jhen/jhen1.jpg","name":"江湖儿女","rank":2,"summary":"故事起始于2001年的山西大同，模特巧巧（赵涛 饰）与出租车公司老板斌哥（廖凡 饰）是一对恋人，斌哥每天在外面呼朋唤友，巧巧却希望两人能够尽快步入婚姻的殿堂。然而一次斌哥在街头遭到竞争对手的袭击，巧巧为了保护斌哥在街头开枪，被判刑五年。巧巧出狱以后，开始寻找斌哥以便重新开始，然而事情却发生了意想不到的变化。"},{"followMovie":false,"id":16,"imageUrl":"http://172.17.8.100/images/movie/stills/dzd6qmwj/dzd6qmwj1.jpg","name":"碟中谍6：全面瓦解","rank":3,"summary":"有时好意会造成恶果，人反而被自己所造成的结果所困扰。伊桑·亨特（汤姆·克鲁斯 饰）和他的IMF团队（亚历克·鲍德温、西蒙·佩吉、文·瑞姆斯）将在最新的电影《碟中谍6：全面瓦解》中再度回归，他们会与观众们熟悉的盟友（丽贝卡·弗格森、米歇尔·莫娜汉）一起与时间赛跑，应对一次任务中出现的意外。亨利·卡维尔、安吉拉·贝塞特和凡妮莎·柯比也将加入本片的演员阵容，电影制片人克里斯托夫·迈考利将会再度担任导演。"},{"followMovie":false,"id":17,"imageUrl":"http://172.17.8.100/images/movie/stills/ftfb3/ftfb(3)1.jpg","name":"反贪风暴3","rank":4,"summary":"ICAC (廉政公署) 陆志廉（古天乐 饰），JFIU (联合财富情报组) 刘保强（张智霖 饰）分别侦查贪污及洗黑钱案，但苦无线索，这时廉政公署L组 (内部纪律调查组) 程德明（郑嘉颖 饰）收到举报，指陆志廉收贿1200万，陆无法辩解实时停职。刘发现陆被诬陷，并跟一直调查的洗黑钱案有着千丝万缕关系，同时怀疑银行主任游子新（栢天男 饰）协助罪恶集团洗黑钱；陆冒险搜集罪证却遭禁锢，命悬一线......."},{"followMovie":false,"id":18,"imageUrl":"http://172.17.8.100/images/movie/stills/hjxd/hjxd1.jpg","name":"黄金兄弟","rank":5,"summary":"狮王（郑伊健 饰）、火山（陈小春 饰）、Bill（谢天华 饰）、淡定（钱嘉乐 饰）、老鼠（林晓峰 饰）五个出生入死的兄弟，在恩师曹sir（曾志伟 饰）的带领下，为了救济儿童而偷取特效药，却惨遭设局，陷入枪林弹雨的险境之中。兄弟们抱着视死如归的豪情，展开一连串的追查与激战。他们明白，即使无法活着回来，也比一人活着痛快！"},{"followMovie":false,"id":21,"imageUrl":"http://172.17.8.100/images/movie/stills/zdn/zdn1.jpg","name":"找到你","rank":6,"summary":"律师李捷（姚晨 饰）正在离婚进行时，与前夫争夺女儿抚养权，拼命工作为给孩子最好的生活，幸有保姆孙芳（马伊琍 饰）帮忙照顾孩子视如己出。一日下班，李捷发现保姆孙芳和女儿毫无预兆地消失了，她内心最大的恐惧变成了现实。在追寻孙芳和女儿的下落时，她收到来自家人的谴责声讨，甚至遭到警方的怀疑。几乎崩溃的李捷，靠着惊人的勇气，踏上独自寻访的旅程。在追踪过程中，李捷逐渐接近了另一个女人\u2014\u2014保姆孙芳的人生故事，她的身份原先都是谎言，而真相也将浮出水面\u2026\u2026"}]
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
         * followMovie : false
         * id : 20
         * imageUrl : http://172.17.8.100/images/movie/stills/ws/ws1.jpg
         * name : 无双
         * rank : 1
         * summary : 以代号“画家”（周润发 饰）为首的犯罪团伙，掌握了制造伪钞技术，难辨真伪，并在全球进行交易获取利益，引起警方高度重视。然而“画家”和其他成员的身份一直成谜，警方的破案进度遭受到了前所未有的挑战。在关键时刻，擅长绘画的李问（郭富城 饰）打开了破案的突破口，而“画家”的真实身份却让众人意想不到……
         */

        private boolean followMovie;
        private int id;
        private String imageUrl;
        private String name;
        private int rank;
        private String summary;

        public boolean isFollowMovie() {
            return followMovie;
        }

        public void setFollowMovie(boolean followMovie) {
            this.followMovie = followMovie;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
