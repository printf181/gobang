package com.what21.chess;
/*
 * 人机对战的算法,对外的接口是getQuan()和getSame()，getQuan()是人机对战是计算出来每个点的权值
 * 取最大的的值作为下棋点，getSame()是用来判断输赢的接口
 * */
public class Computer {
	
	 // q代表棋子 O代表空     先设置权值
private static int Q2O=1;
private static int Q2=10;
private static int Q3O=10;
private static int Q3=100;
private static int Q4O=100;
private static int Q4=2000;
private static int Q5=100000;
private static int level=1;
private int a,b;
static public int num[][]=new int[15][15];
int c[][]=new int[2][2];//记录i，j的起始位置
private int troub1=0,troub2=0,t1,t2;
public void trouble()//困难模式：模拟五步下子
{  
	Q2O=1;
	 Q2=10;
	Q3O=10;
	Q3=100;
	 Q4O=100;
	 Q4=2000;
    Q5=100000;
	level=3;
}
public void trouble1()//中等模式
{
	level=2;
	Q2O=30;
	 Q2=100;
	 Q3O=300;
	 Q3=1200;
	 Q4O=1100;
	 Q4=2000;
	 Q5=10000;
}
public void easy()//简单模式：模拟三步下子
{	Q2O=1;
Q2=10;
Q3O=10;
Q3=100;
Q4O=100; 
Q4=2000;
Q5=100000;
	level=1;
}
public void copyChess(int num1[][])//复制当前棋盘
{
		
	for(int i=0;i<15;i++)
		for(int j=0;j<15;j++)
			num[i][j]=num1[i][j];
}
 boolean generator(int i,int j)//困难模式
//剪枝有邻居的定义是：想个两步以内至少有一个不为空的点即可
//。比如 b[7,7] 有一个子，那么 b[6,7]是他的邻居，b[5,7] 也是，但是 b[4,7] 就不是，因为相隔了三步
{
	int min_i,min_j,max_i,max_j;
	boolean k=false;
	min_i=i-2>=0?i-2:0;
	max_i=i+2<=14?i+2:0;
	min_j=j-2>=0?j-2:14;
	max_j=j+2<=14?j+2:14;
	for(i=min_i;i<=max_i;i++)
		{for(j=min_j;j<=max_j;j++)
			if(num[i][j]!=0){k=true;break;}
		}
	return k;
}
class struct//启发式搜索的struct
{
	int i;
	int j;
	int value=0;
	
}
public  int alphaBeta1(int chess,int alpha ,int beta,int i,int j)//模式选择
{
	if(level==3)return alphaBeta(1, 5, alpha,  beta,i, j);
	else if(level==1)return alphaBeta(1, 1, alpha,  beta,i, j);
	else return getQuan(i,j);
}

/*启发式搜索就是找最优的点有了打分之后，我们就可以按照
 * 分数高低进行排序了。具体实现的时候，是根据按照 成五，活四，双三，活三，其他 的顺序来排序的。
 * 这个难度也比较高，我就按着这个顺序排序，取最优的五个进行下一步循环，大大减少了基数m可以搜索到第六步，*/
private int alphaBeta(int chess, int depth, int alpha, int beta,int i,int j) //alphaBeta剪枝；极大极小搜索，人工智能第一步 模拟五步下子
{
    int best;//最优值
    if( getQuan(i,j,chess%2+1)>=Q5)//五子连珠
    {
	  return getComputerQuan()-getPeopleQuan();//电脑的权值减去人的权值
    }
   else if(depth==0)//depth为0 ，搜索的尽头
    {
    		//System.out.println(getComputerQuan()-getPeopleQuan());
    	return getComputerQuan()-getPeopleQuan();	
    }
    else {
    	struct bestson[]=new struct[225];//寻找最优的五个节点
    	int cnt=0;
    	if(chess==2)//电脑下子
    	{
    		int now;//一个记录当前值的数，
    		for( i = 0;i<= 14;i++)
	  for(j = 0;j<=14;j++)
	  {
		  if(num[i][j]==0)
		  {
			  if(alpha>=beta)return alpha;//alpha剪枝
			  else if(generator( i,j)==true){	//相邻剪枝
				  num[i][j]=2;
				 // now=getQuan(i,j,chess);
				  now=getQuan(i,j,1)+getQuan(i,j,2);
				  num[i][j]=0;
				  bestson[cnt]= new struct();//入队
				  bestson[cnt].i=i;
				  bestson[cnt].j=j;
				  bestson[cnt++].value=now;
			  }
		  }
	  }
    		struct t=new struct();
    		for(i=0;i<cnt;i++)//冒泡排序
    			for(j=i+1;j<cnt;j++)
    				if(bestson[i].value<bestson[j].value)
    				{
    					t=bestson[i];
    					bestson[i]=bestson[j];
    					bestson[j]=t;
    				}
    		cnt=cnt<5?cnt:5;
    		for(i=0;i<cnt;i++)//启发式搜索，取前五
    		{
    			num[bestson[i].i][bestson[i].j]=2;
				  now=alphaBeta(1,depth-1,alpha,beta,bestson[i].i,bestson[i].j);
				  if(now>alpha)alpha=now;
				  num[bestson[i].i][bestson[i].j]=0;
    		}
    	best=alpha;	
    	}
    	else {//人下子
    		int now;
    		cnt=0;
    		for( i = 0;i<= 14;i++)
	  for(j = 0;j<=14;j++)
	  {
		  if(num[i][j]==0)
		  {
			  if(alpha>=beta)return beta;//beat剪枝
			  else if(generator(i,j)==true){//相邻剪枝
				  num[i][j]=1;
				  now=getQuan(i,j,1)+getQuan(i,j,2);
				  num[i][j]=0;
				  bestson[cnt]= new struct();//入队
				  bestson[cnt].i=i;
				  bestson[cnt].j=j;
				  bestson[cnt++].value=now;
			  }
		  }
	  }
    		struct t=new struct();
    		for(i=0;i<cnt;i++)//冒泡排序
    			for(j=i+1;j<cnt;j++)
    				if(bestson[i].value<bestson[j].value)
    				{
    					t=bestson[i];
    					bestson[i]=bestson[j];
    					bestson[j]=t;
    				}
    		cnt=cnt<5?cnt:5;
    		for(i=0;i<cnt;i++)//启发式搜索，取前五
    		{
    			   num[bestson[i].i][bestson[i].j]=1;
				  now=alphaBeta(2,depth-1,alpha,beta,bestson[i].i,bestson[i].j);
				  if(now<beta)beta=now;  
				  num[bestson[i].i][bestson[i].j]=0;
    		}
    	best=beta;	
    	}
    }
     return best;
} /*
private int alphaBeta2(int chess, int depth, int alpha, int beta,int i,int j) //alphaBeta剪枝；极大极小博弈树，人工智能第一步 模拟五步下子
{
    int best;
   if( getQuan(i,j,chess%2+1)>=Q5)//五子连珠
    {
	  return getComputerQuan()-getPeopleQuan();//电脑的权值减去人的权值
    }
   else if(depth==0)//depth为0 ，搜索的尽头
    {
    		//System.out.println(getComputerQuan()-getPeopleQuan());
    	return getComputerQuan()-getPeopleQuan();	
    }
    else {
    	if(chess==2)
    	{
    		int now;
    		for( i = 0;i<= 14;i++)
	  for(j = 0;j<=14;j++)
	  {
		  if(num[i][j]==0)
		  {
			  if(alpha>=beta)return alpha;
			  else if(generator( i,j)==true){	
				  num[i][j]=2;
				  now=alphaBeta2(1,depth-1,alpha,beta,i,j);
				  num[i][j]=0;			
				  if(now>alpha)alpha=now;
			  }
		  }
	  }
    		
    	best=alpha;	
    	}
    	else {
    		int now;
    		for( i = 0;i<= 14;i++)
	  for(j = 0;j<=14;j++)
	  {
		  if(num[i][j]==0)
		  {
			  if(alpha>=beta)return beta;
			  else if(generator(i,j)==true){
				  num[i][j]=1;
				  now=alphaBeta2(2,depth-1,alpha,beta,i,j);
				  num[i][j]=0;
				  if(now<beta)beta=now;  
			  }
		  }
	  }
    	best=beta;	
    	}
    }
     return best;
} */
private int getPeopleQuan()//目前棋盘人的权值
{
	int sum=0;
	for(int i=0;i<14;i++)
	{
		for(int j=0;j<=14;j++)
			if(num[i][j]==1)sum+=getQuan(i,j,1);
	}
	return sum;
}
private int getComputerQuan()//目前棋盘电脑的权值
{
	int sum=0;
	for(int i=0;i<14;i++)
	{
		for(int j=0;j<=14;j++)
			if(num[i][j]==2)sum+=getQuan(i,j,2);
	}
	return sum;
}
public int getQuan(int i,int j,int chess)//每个位置的权值
{
	int q=0;
	q+=getQuan0(i,j,chess);//4个方向的权值
	q+=getQuan90(i,j,chess);
	q+=getQuan135(i,j,chess);
	q+=getQuan45(i,j,chess);
	 return q;
}

private int getQuan0(int i,int j,int chess)//水平权值
{
	a=1;
	b=0;
	if(chess==1)return getquanPeople(i,j);
	else return getquanComputer(i,j);
}

private int getQuan90(int i,int j,int chess)//90度角权值
{
	a=0;
	b=1;
	if(chess==1)return getquanPeople(i,j);
	else return getquanComputer(i,j);
}
private int getQuan135(int i,int j,int chess)//135度角权值
{
	a=1;
	b=-1;
	if(chess==1)return getquanPeople(i,j);
	else return getquanComputer(i,j);
}
private int getQuan45(int i,int j,int chess)//45度角权值
{
	a=1;
	b=1;
	if(chess==1)return getquanPeople(i,j);
	else return getquanComputer(i,j);
}
private void initc(int i,int j)
{
	c[0][0]=c[0][1]=i;
	c[1][0]=c[1][1]=j;
}
private int getquanPeople(int i,int j)//计算自己和对方的权值相加就是最大权值
{
	int samechesSnums=0;//相同棋子个数
	int blankNums=0;
	int qs=0;
	initc(i,j);
	//计算人的权值
	samechesSnums=getSamechessNums(1);//得到棋子的数量
	if(c[0][0]>=0&&c[0][0]<=14&&c[1][0]>=0&&c[1][0]<=14)//得到空的数量
		if(num[c[0][0]][c[1][0]]==0)blankNums++;
	
	if(c[0][1]>=0&&c[0][1]<=14&&c[1][1]>=0&&c[1][1]<=14)	
		{
		if(num[c[0][1]][c[1][1]]==0)blankNums++;
		}
		qs=getQuanEqual(samechesSnums,blankNums);	
	return qs;
}
private int getquanComputer(int i,int j)
{
	int samechessNumf=0;//相同棋子的数量
	int blankNumf=0;//两边被堵几边
	int qf=0;
	//计算自己的权值
			initc(i,j);
			samechessNumf=getSamechessNums(2);//得到棋子的数量
			if(c[0][0]>=0&&c[0][0]<=14&&c[1][0]>=0&&c[1][0]<=14)//得到空的数量
				if(num[c[0][0]][c[1][0]]==0)blankNumf++;
			
			if(c[0][1]>=0&&c[0][1]<=14&&c[1][1]>=0&&c[1][1]<=14)	
				if(num[c[0][1]][c[1][1]]==0)blankNumf++;
			qf=getQuanEqual(samechessNumf,blankNumf);
			return qf;
}
private int  getSamechessNums(int id) 
//得到棋子的数量
{
	int num1=1;
	c[0][0]+=a;//右方向
	c[1][0]+=b;
	while(c[0][0]>=0&&c[0][0]<=14&&num1<5&&c[1][0]>=0&&c[1][0]<=14)
	{
		if(num[c[0][0]][c[1][0]]!=id)break;
		num1++;
		c[0][0]+=a;
		c[1][0]+=b;
	}
	//左方向
	c[0][1]-=a;
	c[1][1]-=b;
	while(c[1][1]>=0&&c[1][1]<=14&&num1<5&&c[0][1]>=0&&c[0][1]<=14)
	{
		if(num[c[0][1]][c[1][1]]!=id)break;
		num1++;
		c[0][1]-=a;
		c[1][1]-=b;
	}
	return num1;
}
private int getQuanEqual(int chess,int blank)
{
      if(chess==2&&blank==1)return Q2O;
	else if(chess==2&&blank==2)return Q2;
	else if(chess==3&&blank==1)return Q3O;
	else if(chess==3&&blank==2)return Q3;
	else if(chess==4&&blank==1)return Q4O;
	else if(chess==4&&blank==2)return Q4;
	else if(chess==5)return Q5;
	else return 0;
}
public int getSame(int i,int j,int id)//判断输赢
{
	int q=0,k=0;
	a=1;b=0;initc(i,j);
	k=getSamechessNums(id);
	q=q<k?k:q;
	
	a=0;b=1;initc(i,j);
	k=getSamechessNums(id);
	q=q<k?k:q;

	a=1;
	b=1;initc(i,j);
	k=getSamechessNums(id);
	q=q<k?k:q;

	a=1;b=-1;initc(i,j);
	k=getSamechessNums(id);
	q=q<k?k:q;
	return q;
}





//开始做的，中等人机
private int getQuan(int i,int j)//每个位置的权值
{
	int q=0;
	q+=getQuan0(i,j);//4个方向的权值
	q+=getQuan90(i,j);
	q+=getQuan135(i,j);
	q+=getQuan45(i,j);
	return q;
}

private int getQuan0(int i,int j)
{
	a=1;
	b=0;
	return getquanALL(i,j);
}
private int getQuan90(int i,int j)
{
	a=0;
	b=1;
	return getquanALL(i,j);
}
private int getQuan135(int i,int j)
{
	a=1;
	b=-1;
	return getquanALL(i,j);
}
private int getQuan45(int i,int j)
{
	a=1;
	b=1;
	return getquanALL(i,j);
}
private int getquanALL(int i,int j)//计算自己和对方的权值相加就是最大权值
{
	int samechesSnums=0;//相同旗子个数
	int samechessNumf=0;
	int blankNums=0;
	int blankNumf=0;
	int q=0,qs=0,qf=0;
	initc(i,j);
	//计算人的权值
	samechesSnums=getSamechessNums(1);//得到棋子的数量
	if(c[0][0]>=0&&c[0][0]<=14&&c[1][0]>=0&&c[1][0]<=14)//得到空的数量
		if(num[c[0][0]][c[1][0]]==0)blankNums++;
	
	if(c[0][1]>=0&&c[0][1]<=14&&c[1][1]>=0&&c[1][1]<=14)	
		{
		if(num[c[0][1]][c[1][1]]==0)blankNums++;
		}
		qs=getQuanEqual(samechesSnums,blankNums);
		
		//计算自己的权值
		initc(i,j);
		samechessNumf=getSamechessNums(2);//得到棋子的数量
		if(c[0][0]>=0&&c[0][0]<=14&&c[1][0]>=0&&c[1][0]<=14)//得到空的数量
			if(num[c[0][0]][c[1][0]]==0)blankNumf++;
		
		if(c[0][1]>=0&&c[0][1]<=14&&c[1][1]>=0&&c[1][1]<=14)	
			if(num[c[0][1]][c[1][1]]==0)blankNumf++;
		qf=getQuanEqual(samechessNumf,blankNumf);
		q=qs+qf;
		
	return q;
}
}
