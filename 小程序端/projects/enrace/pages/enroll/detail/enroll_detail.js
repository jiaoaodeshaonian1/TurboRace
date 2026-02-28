const cloudHelper = require('../../../../../helper/cloud_helper.js');
const pageHelper = require('../../../../../helper/page_helper.js');
const ProjectBiz = require('../../../biz/project_biz.js');
const projectSetting = require('../../../public/project_setting.js');
const PassportBiz = require('../../../../../comm/biz/passport_biz.js');
const EnrollBiz = require('../../../biz/enroll_biz.js');

Page({
	/**
	 * 页面的初始数据
	 */
	data: {
		isLoad: false,
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: async function (options) {
		ProjectBiz.initPage(this);
		if (!pageHelper.getOptions(this, options)) return;
		this._loadDetail();

		wx.setNavigationBarColor({
			backgroundColor: projectSetting.PROJECT_COLOR,
			frontColor: '#ffffff',
		})
	},

	_loadDetail: async function () {
		let id = this.data.id;
		if (!id) return;

		let params = {
			id,
		};
		let opt = {
			title: 'bar'
		};
		let enroll = await cloudHelper.callCloudData('enroll/view', params, opt);
		if (!enroll) {
			this.setData({
				isLoad: null
			})
			return;
		}
		enroll.enrollObj = JSON.parse(enroll.enrollObj);

		this.setData({
			isLoad: true,
			enroll,
		});
	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function () { },

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow: function () { },

	/**
	 * 生命周期函数--监听页面隐藏
	 */
	onHide: function () { },

	/**
	 * 生命周期函数--监听页面卸载
	 */
	onUnload: function () { },

	/**
	 * 页面相关事件处理函数--监听用户下拉动作
	 */
	onPullDownRefresh: async function () {
		await this._loadDetail();
		wx.stopPullDownRefresh();
	},

	/**
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom: function () { },

	bindJoinTap: async function (e) {
		if (!await PassportBiz.loginMustCancelWin(this)) return;

		let cb = async () => {
			try {
				let opts = {
					title: '提交中'
				}
				let params = {
					enrollId: this.data.id,
				}
				await cloudHelper.callCloudSumbit('enroll/join', params, opts).then(res => {
					let that = this;
					wx.showModal({
						title: '温馨提示',
						showCancel: false,
						content: '报名成功！',
						success() {
							wx.redirectTo({
								url: 'enroll_detail?id=' + that.data.id,
							})
						}
					})
				})
			} catch (err) {
				console.log(err);
			};
		}

		pageHelper.showConfirm('您确认报名？', cb);

	},

	bindCancelJoinTap: async function (e) {
		if (!await PassportBiz.loginMustCancelWin(this)) return;
		let cb = () => {
			wx.redirectTo({
				url: 'enroll_detail?id=' + this.data.id,
			})
		}
		await EnrollBiz.cancelMyEnrollJoin(this.data.enroll.myEnrollJoinId, cb);
	},

	url: function (e) {
		pageHelper.url(e, this);
	},

	onPageScroll: function (e) {
		// 回页首按钮
		pageHelper.showTopBtn(e, this);

	},

	onShareAppMessage: function (res) {
		return {
			title: this.data.enroll.enrollTitle,
			imageUrl: this.data.enroll.enrollObj.cover[0]
		}
	}
})