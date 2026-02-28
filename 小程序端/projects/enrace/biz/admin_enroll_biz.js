/**
 * Notes: 活动后台管理模块业务逻辑
 * Ver : CCMiniCloud Framework 2.0.1 ALL RIGHTS RESERVED BY cclinux0730 (wechat)
 * Date: 06-24 07:48:00 
 */

const BaseBiz = require('../../../comm/biz/base_biz.js');
const EnrollBiz = require('./enroll_biz.js');
const projectSetting = require('../public/project_setting.js'); 

class AdminEnrollBiz extends BaseBiz {
	static initFormData(id = '') {
		let cateIdOptions = EnrollBiz.getCateList();

		return {
			id,

			cateIdOptions,
			fields: projectSetting.ENROLL_FIELDS,

			formTitle: '',
			formCateId: (cateIdOptions.length == 1) ? cateIdOptions[0].val : '',
			formOrder: 9999,

			formMaxCnt: 50,
			formStart: '', 
			 

			formForms: [],
 
		}

	}

	
}

AdminEnrollBiz.CHECK_FORM = {
	title: 'formTitle|must|string|min:2|max:50|name=标题',
	cateId: 'formCateId|must|id|name=分类',
	order: 'formOrder|must|int|min:0|max:9999|name=排序号',

	maxCnt: 'formMaxCnt|must|int|name=人数上限',
	start: 'formStart|must|string|name=报名开始时间',
	end: 'formEnd|must|string|name=报名结束时间',   
 
};

module.exports = AdminEnrollBiz;