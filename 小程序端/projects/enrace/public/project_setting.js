module.exports = { // enrace
	PROJECT_COLOR: '#FF4343',
	NAV_COLOR: '#ffffff',
	NAV_BG: '#FF4343',

	// setup
	SETUP_CONTENT_ITEMS: [
		{ title: '关于我们', key: 'SETUP_CONTENT_ABOUT' },
		{ title: '用户注册使用协议', key: 'SETUP_YS' }
	],

	// 用户 
	USER_FIELDS: [
		{ mark: 'sex', title: '性别', type: 'select', selectOptions: ['男', '女'], must: true },
		{ mark: 'birth', type: 'date', title: '出生年月', must: true },
		{ mark: 'college', type: 'text', title: '学院系所', must: true, min: 2, max: 200 },
		{ mark: 'major', type: 'text', title: '所学专业', must: true, min: 2, max: 200 },
		{ mark: 'item', type: 'text', title: '班级', must: true, min: 2, max: 200 },
		{ mark: 'year', type: 'year', title: '入学年份', must: true, },
		{ mark: 'no', type: 'text', title: '学号', must: true, },

	],


	NEWS_NAME: '公告通知',
	NEWS_CATE: [
		{ id: 1, title: '公告通知' }, 
	],
	NEWS_FIELDS: [
		{ mark: 'desc', type: 'textarea', title: '简介', must: true, min: 2, max: 200 },
		{ mark: 'content', title: '详细内容', type: 'content', must: true },
		{ mark: 'cover', type: 'image', title: '封面图', must: true, min: 1, max: 1 },
	],
 
	ENROLL_NAME: '竞赛',
	ENROLL_CATE: [
		{ id: 1, title: '学科竞赛' },
		{ id: 2, title: '体育户外' },
		{ id: 3, title: '创业职场' },
		{ id: 4, title: '编程AI' },
		{ id: 5, title: '歌唱文艺' },
		{ id: 6, title: '工程实践' },
		{ id: 7, title: '演讲辩论' },
		{ id: 8, title: '其他比赛' },

	],
	ENROLL_FIELDS: [
		{ mark: 'cover', title: '封面', type: 'image', min: 1, max: 1, must: true },
		{ mark: 'unit', title: '主办单位', type: 'textarea', must: true }, 
		{ mark: 'time', title: '比赛时间', type: 'text', must: true }, 
		{ mark: 'address', title: '比赛地点', type: 'text', must: true },
		{ mark: 'desc', title: '详细介绍', type: 'content', must: true },
	],
	ENROLL_JOIN_FIELDS: [
		{ mark: 'name', type: 'text', title: '姓名', must: true, max: 30 },
		{ mark: 'phone', type: 'mobile', title: '手机', must: true, edit: false }
	],


}