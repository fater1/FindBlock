package xl.fragmentexample.find_block.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者： li.mr
 * 时间： 2017/5/22
 */

public class SortInfo implements Serializable{
    public String typeId;
    public String name;
    public String errormessage;
    public String status;
    public int total;
    public String isCare;
    public String anchorId;
    public String url;
    public List<Listcontent> list  ;
    public List<HuaDongListBean> typeName  ;

    public class Listcontent {

        public String id;
        public String name;
        public String type;
        public String num;
        public String anchorId;
        public String nickname;
        public String icon;
        public String roomname;
        public String roomimg;
        public String online;
        public String url;
        public String remark;
        public String filetype;

        public String getFiletype() {
            return filetype;
        }

        public void setFiletype(String filetype) {
            this.filetype = filetype;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getAnchorId() {
            return anchorId;
        }

        public void setAnchorId(String anchorId) {
            this.anchorId = anchorId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getRoomimg() {
            return roomimg;
        }

        public void setRoomimg(String roomimg) {
            this.roomimg = roomimg;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIsCare() {
        return isCare;
    }

    public void setIsCare(String isCare) {
        this.isCare = isCare;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Listcontent> getList() {
        return list;
    }

    public void setList(List<Listcontent> list) {
        this.list = list;
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

    public List<HuaDongListBean> getTypeName() {
        return typeName;
    }

    public void setTypeName(List<HuaDongListBean> typeName) {
        this.typeName = typeName;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
