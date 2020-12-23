package bb.webhook.hmac

import cats.effect.Sync

final case class SecretKey(value: String) extends AnyVal


trait SignatureVerifier[F[_]] {
  def verify(key: SecretKey)(message: String): F[Boolean]
}

object SignatureVerifier {
  def apply[F[_]: Sync]: SignatureVerifier[F] = impl.signatureVerifier[F]
}
