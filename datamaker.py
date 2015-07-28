import json
import random
import time

class JEncoder(json.JSONEncoder):
	def default(self, obj):
		if not isinstance(obj, Asin):
			return super(JEncoder, self).default(obj)
		return obj.__dict__

class Asin(object):
	def __init__(self, asin, price, dph, fcsts):
		self.__asin = asin
		self.__price = price
		self.__dph = dph
		self.__fcsts = fcsts
	def getAsinName(self):
		return self.__asin
	def __str__(self):
		return "asin:%s, price:%s, dph:%s, fcsts:%s"%(self.__asin, self.__price, self.__dph, str(self.__fcsts))

class AsinFactory(object):
	@staticmethod
	def createAsin():
		return Asin(AsinFactory.createAsinName(), AsinFactory.createPrice(), AsinFactory.createDPH(), AsinFactory.createFCST())
	@staticmethod
	def createAsinName():
		def getOneChar():
			bchar = random.randint(0,1)
			if bchar == 0:
				return str(random.randint(0,9))
			else:
				return chr(ord('a') + random.randint(0, 25))

		asin = ""
		for i in range(10):
			asin += getOneChar()
		return asin
	@staticmethod
	def createPrice():
		return random.randint(10, 100)
	@staticmethod
	def createDPH():
		return random.randint(400, 1000)
	@staticmethod
	def createFCST():
		return [random.randint(1,100) for i in range(14)]
	
class Transmuter(object):
	container = list()

	@staticmethod
	def addAsin(asin):
		Transmuter.container.append(asin)

	@staticmethod
	def transmute():
		ret = ""
		for asin in Transmuter.container:
			ret += json.dumps(asin, cls=JEncoder) + "\n"
		ret.strip()
		return ret


if __name__  == '__main__':
	for i in range(100):
		asin = AsinFactory.createAsin()
		Transmuter.addAsin(asin)
		#print(asin)

	ret = Transmuter.transmute().replace("_Asin__","")

	with open(time.strftime("%H-%M-%S", time.gmtime()), 'w') as fid:
		fid.write(ret)


