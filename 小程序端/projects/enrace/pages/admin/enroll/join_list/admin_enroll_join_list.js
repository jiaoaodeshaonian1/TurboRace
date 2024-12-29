const AdminBiz = require('../../../../../../comm/biz/admin_biz.js');
const pageHelper = require('../../../../../../helper/page_helper.js');
const helper = require('../../../../../../helper/helper.js');
const cloudHelper = require('../../../../../../helper/cloud_helper.js');


Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		isLoad: false,



		enrollId: '',

		title: '',
		titleEn: '',
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		if (!AdminBiz.isAdmin(this)) return;

		// 附加参数 
		if (options && options.enrollId) {
			//设置搜索菜单 
			this._getSearchMenu();

			this.setData({
				enrollId: options.enrollId,
				_params: {
					enrollId: options.enrollId
				}
			}, () => {
				this.setData({
					isLoad: true
				});
			});
		}

		if (options && options.title) {
			let title = decodeURIComponent(options.title);
			this.setData({
				title,
				titleEn: options.title
			});
			wx.setNavigationBarTitle({
				title: '报名名单管理 - ' + title
			});
		}
	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function () {

	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow: function () {

	},

	/**
	 * 生命周期函数--监听页面隐藏
	 */
	onHide: function () {

	},

	/**
	 * 生命周期函数--监听页面卸载
	 */
	onUnload: function () {

	},

	url: async function (e) {
		pageHelper.url(e, this);
	},




	bindCopyTap: function (e) {
		let idx = pageHelper.dataset(e, 'idx');
		let forms = this.data.dataList.list[idx].enrollJoinForms;

		let ret = '';

		if (this.data.title)
			ret += `活动：${this.data.title}\r`;

		for (let k = 0; k < forms.length; k++) {
			ret += forms[k].title + '：' + forms[k].val + '\r';
		}
		wx.setClipboardData({
			data: ret,
			success(res) {
				wx.getClipboardData({
					success(res) {
						pageHelper.showNoneToast('已复制到剪贴板');
					}
				})
			}
		});

	}, 

	bindDelTap: async function (e) {

		let callback = async () => {
			let idx = Number(pageHelper.dataset(e, 'idx'));
			let dataList = this.data.dataList;
			let enrollJoinId = dataList.list[idx].enrollJoinId;
			let params = {
				enrollJoinId
			}
			let opts = {
				title: '删除中'
			}
			try {
				await cloudHelper.callCloudSumbit('admin/enroll/join/del', params, opts).then(res => {

					let cb = () => {
						let dataList = this.data.dataList;
						dataList.list.splice(idx, 1);
						dataList.total--;
						this.setData({
							dataList
						});
					}

					pageHelper.showSuccToast('删除成功', 1000, cb);
				});
			} catch (err) {
				console.error(err);
			}
		}

		pageHelper.showConfirm('确认删除该报名记录？ 删除后用户将无法查询到本报名记录', callback);


	},

	bindCommListCmpt: function (e) {
		pageHelper.commListListener(this, e);

	},

	// 修改与展示状态菜单
	_getSearchMenu: function () {

		let sortItems = [];
		let sortMenus = [
			{ label: '全部', type: '', value: '' }, 
		];
		this.setData({
			sortItems,
			sortMenus:[]
		})


	}

})