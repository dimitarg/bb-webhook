package bb.webhook

import cats.effect.{Blocker, ContextShift, IO}
import cats.effect.Resource
import scala.sys.process._
import java.nio.file.{Path, Paths}
import java.io.FileOutputStream
import java.nio.file.attribute.PosixFilePermissions

package object testutil {

  val mkTestResources: Resource[IO, TestResources] = for {
    blocker <- Blocker[IO]
  } yield TestResources(blocker)

  def tmpClasspathResource(blocker: Blocker)(resourcePath: String, perms: Option[String] = None)(implicit shift: ContextShift[IO]): Resource[IO, Path] = {

    val attr = perms.map { x=>
      PosixFilePermissions.asFileAttribute(PosixFilePermissions.fromString(x))
    }.toSeq

    for {
      result <- fs2.io.file.tempFileResource[IO](blocker = blocker, dir = Paths.get("."), attributes = attr)
      contents = fs2.io.readInputStream[IO](IO.delay(getClass().getResourceAsStream(resourcePath)), 1024, blocker)
      out = contents.through(fs2.io.writeOutputStream[IO](IO(new FileOutputStream(result.toFile())), blocker))
      _ <- Resource.liftF(out.compile.drain)
    } yield result
  }

  def execProcess(blocker: Blocker)(command: String, args: List[String])(implicit shift: ContextShift[IO]): IO[String] = {
    blocker.delay[IO, String] {
      Process(command, args).!!
    }
  }
}
