package xl.fragmentexample.find_block.bean;

/**
 * 作者： li.mr
 * 时间： 2017/7/24
 */

public class HuaDongListBean {
    public String typeId;
    public String name;

    public HuaDongListBean(String typeId, String name) {
        this.typeId = typeId;
        this.name = name;
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
}
