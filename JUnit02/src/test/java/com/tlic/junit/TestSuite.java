package com.tlic.junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class) // �����������
@SuiteClasses({TestA.class,
				TestB.class,
				TestCalcuate.class})
public class TestSuite {

	/*
	 * ����ԭ��
	 * 	1�����鴴��һ��ר�ŵ�source folder-->test����д���������
	 *  2��������İ�Ӧ�ñ��ֺ���Ҫ���Ե���һ��
	 *  3�����Ե�Ԫ�е�ÿһ�����Է������������ִ�У�û��˳��
	 *     ���Է���֮�䲻�����κε������� 
	 */
}
