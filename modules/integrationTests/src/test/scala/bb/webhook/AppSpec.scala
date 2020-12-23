package bb.webhook
import weaver.pure._
import fs2.Stream
import cats.effect.{IO, ExitCode}

object AppSpec {
  def tests: Stream[IO, Test] = Stream(
    test("the application can start") {
      Main.run(List()).map { result =>
        expect(result == ExitCode.Success)
      }
    }
  )
}
