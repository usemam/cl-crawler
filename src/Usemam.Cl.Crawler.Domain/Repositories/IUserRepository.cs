using Usemam.Cl.Crawler.Domain.Model;

namespace Usemam.Cl.Crawler.Domain.Repositories
{
    public interface IUserRepository
    {
        User GetByEmail(string email);

        void Save(User user);
    }
}
