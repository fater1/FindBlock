package cn.bluemobi.baseframe.view.imagecycleview;

/**
 * 描述：广告信息</br>
 */
public class ADInfo {
	String id = "";
	String imageUrl = "";
	String content = "";
	String type = "";
	String url = "";
	String title = "";
	Object bannerInfo;
	String gameUrl = null;
	String name = null;
	String flag = null;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Object getBannerInfo() {
		return bannerInfo;
	}

	public void setBannerInfo(Object bannerInfo) {
		this.bannerInfo = bannerInfo;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
}
