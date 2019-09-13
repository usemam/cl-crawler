using System.Collections.Generic;
using Usemam.Cl.Crawler.Domain.Model;

namespace Usemam.Cl.Crawler.Domain.Repositories
{
    public interface IUserRepository
    {
        IEnumerable<string> GetAllUserEmails();

        User GetByEmail(string email);

        void Save(User user);
    }
}
