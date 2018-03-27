package xl.fragmentexample.find_block.bean;

import java.util.List;

/**
 * 作者： li.mr
 * 时间： 2017/5/22
 */

public class Find_Two_Bean {
    public String errormessage;
    public String status;
    public List<GameBanner> gameBanner;
    public List<Sort> sort;
    public List<SortInfo> sortInfo;
    public List<SingleBanner> singleBanner;
    public List<Banner> banner;

    public class GameBanner {
        public String typeId;
        public String pic;
        public String name;
        public String gameUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getGameUrl() {
            return gameUrl;
        }

        public void setGameUrl(String gameUrl) {
            this.gameUrl = gameUrl;
        }
    }

    public class Sort {
        public String typeId;
        public String pic;
        public String name;
        public String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public class SingleBanner {
        public String typeId;
        public String pic;
        public String gameUrl;

        public String getGameUrl() {
            return gameUrl;
        }

        public void setGameUrl(String gameUrl) {
            this.gameUrl = gameUrl;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public class Banner {
        public String typeId;
        public String pic;
        public String name;
        public String gameUrl;
        public String flag;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getGameUrl() {
            return gameUrl;
        }

        public void setGameUrl(String gameUrl) {
            this.gameUrl = gameUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GameBanner> getGameBanner() {
        return gameBanner;
    }

    public void setGameBanner(List<GameBanner> gameBanner) {
        this.gameBanner = gameBanner;
    }

    public List<Sort> getSort() {
        return sort;
    }

    public void setSort(List<Sort> sort) {
        this.sort = sort;
    }

    public List<SortInfo> getSortInfo() {
        return sortInfo;
    }

    public void setSortInfo(List<SortInfo> sortInfo) {
        this.sortInfo = sortInfo;
    }

    public List<SingleBanner> getSingleBanner() {
        return singleBanner;
    }

    public void setSingleBanner(List<SingleBanner> singleBanner) {
        this.singleBanner = singleBanner;
    }

    public List<Banner> getBanner() {
        return banner;
    }

    public void setBanner(List<Banner> banner) {
        this.banner = banner;
    }
}
