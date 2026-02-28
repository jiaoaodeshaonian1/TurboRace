const AdminBiz = require('../../../../../../comm/biz/admin_biz.js');
const ProjectBiz = require('../../../../biz/project_biz.js');
const pageHelper = require('../../../../../../helper/page_helper.js');
const cloudHelper = require('../../../../../../helper/cloud_helper.js');
const dataHelper = require('../../../../../../helper/data_helper.js');
const validate = require('../../../../../../helper/validate.js');
const EnrollBiz = require('../../../../biz/enroll_biz.js');
const AdminEnrollBiz = require('../../../../biz/admin_enroll_biz.js');
const projectSetting = require('../../../../public/project_setting.js');

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
		if (!AdminBiz.isAdmin(this)) return;
		if (!pageHelper.getOptions(this, options)) return;

		wx.setNavigationBarTitle({
			title: projectSetting.ENROLL_NAME + '-修改',
		});

		this._loadDetail();
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
		this.selectComponent("#cmpt-form").reload();
		wx.stopPullDownRefresh();
	},

	model: function (e) {
		pageHelper.model(this, e);
	},

	_loadDetail: async function () {
		if (!AdminBiz.isAdmin(this)) return;

		let id = this.data.id;
		if (!id) return;

		if (!this.data.isLoad) this.setData(AdminEnrollBiz.initFormData(id)); // 初始化表单数据

		let params = {
			id
		};
		let opt = {
			title: 'bar'
		};
		let enroll = await cloudHelper.callCloudData('admin/enroll/detail', params, opt);
		if (!enroll) {
			this.setData({
				isLoad: null
			})
			return;
		};


		this.setData({
			isLoad: true,

			formTitle: enroll.enrollTitle,
			formCateId: enroll.enrollCateId,
			formOrder: enroll.enrollOrder,

			formMaxCnt: enroll.enrollMaxCnt,
			formStart: enroll.enrollStart,
			formEnd: enroll.enrollEnd,

			formForms: JSON.parse(enroll.enrollForms),

		});

	},

	bindFormSubmit: async function () {
		if (!AdminBiz.isAdmin(this)) return;

		// 数据校验
		let data = this.data;
		data = validate.check(data, AdminEnrollBiz.CHECK_FORM, this);
		if (!data) return;

		if (data.end < data.start) {
			return pageHelper.showModal('结束时间不能早于开始时间');
		}

		let forms = this.selectComponent("#cmpt-form").getForms(true);
		if (!forms) return;

		data.forms = JSON.stringify(forms);
		data.obj = JSON.stringify(dataHelper.dbForms2Obj(forms));

		data.cateName = EnrollBiz.getCateName(data.cateId);

		try {
			let enrollId = this.data.id;
			data.id = enrollId;

			// 先修改，再上传 
			await cloudHelper.callCloudSumbit('admin/enroll/edit', data).then(res => {
				// 更新列表页面数据
				let node = {
					'enrollTitle': data.title,
					'enrollCateName': data.cateName,
					'enrollOrder': data.order,
					'enrollStart': data.start,
					'enrollEnd': data.end,
					'enrollMaxCnt': data.maxCnt,
					statusDesc: res.data.statusDesc
				}
				pageHelper.modifyPrevPageListNodeObject(enrollId, node, 2, 'dataList', 'enrollId');
			});


			let callback = () => {
				wx.navigateBack();
			}
			pageHelper.showSuccToast('修改成功', 2000, callback);

		} catch (err) {
			console.log(err);
		}

	},

	bindMapTap: function (e) {
		ProjectBiz.selectLocation(this);
	},

	url: function (e) {
		pageHelper.url(e, this);
	},

	switchModel: function (e) {
		pageHelper.switchModel(this, e);
	},


})