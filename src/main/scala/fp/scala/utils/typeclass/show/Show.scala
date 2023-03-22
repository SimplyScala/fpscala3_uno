package fp.scala.utils.typeclass.show

import magnolia1.*

trait Show[T]:
	def apply(x: T): String

object Show extends AutoDerivation[Show] :
	def join[T](ctx: CaseClass[Show, T]): Show[T] = value => {
		if (ctx.params.isEmpty) ctx.typeInfo.short else
			ctx.params.map { param =>
				param.typeclass(param.deref(value))
			}.mkString(s"${ctx.typeInfo.short}(", ",", ")")
	}

	override def split[T](ctx: SealedTrait[Show, T]): Show[T] = value =>
		ctx.choose(value) { sub => sub.typeclass(sub.cast(value)) }

	given Show[Int] = _.toString

	given Show[String] = x => "\"$x\""

	extension[T] (x: T)(using s: Show[T]) { def show: String = s(x) }